package com.example.janackoverflow.community.service;

import com.example.janackoverflow.community.domain.MediumArticle;
import com.example.janackoverflow.community.entity.Comment;
import com.example.janackoverflow.community.repository.CommentRepository;
import com.example.janackoverflow.community.repository.CommunityRepositoryImpl;
import com.example.janackoverflow.issue.domain.IssueDTO;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.issue.repository.IssueRepository;
import com.example.janackoverflow.user.repository.UsersRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.*;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
@Slf4j
public class CommunityService {
    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final UsersRepository usersRepository;
    private final LikesService likesService;

    private final CommunityRepositoryImpl communityRepositoryImpl;

    public CommunityService (CommentRepository commentRepository,
                             IssueRepository issueRepository,
                             LikesService likesService,
                             UsersRepository usersRepository,
                             CommunityRepositoryImpl communityRepositoryImpl) {

        this.commentRepository = commentRepository;
        this.issueRepository = issueRepository;
        this.likesService = likesService;
        this.usersRepository = usersRepository;
        this.communityRepositoryImpl = communityRepositoryImpl;
    }

    public Page<IssueDTO.ResponseDTO> getSolvedIssueList(String order, String category) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, order));
        // 페이져블 객체와 errorId로 모든 comment를 들고온다.
        Page<Issue> issueList = issueRepository.findAll(pageable);
        // 댓글DtoList
        List<IssueDTO.ResponseDTO> issueResponseDtoList = issueList.stream().map(issue -> issue.toDto(likesService.getIssueLikes(issue.getId()), usersRepository.findById(issue.getUsers().getId()).get().toIssueDto())).toList();

        Page<IssueDTO.ResponseDTO> issuePage = new PageImpl<>(issueResponseDtoList, pageable, issueList.getTotalElements());

        log.info("get Content : " + issuePage.getContent().get(0));
        log.info("page : " + issuePage.getTotalPages());
        log.info("total : " + issuePage.getTotalElements());

        return issuePage;
    }

    public Page<IssueDTO.ResponseDTO> searchKeyword(String keyword, String title) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));
        return communityRepositoryImpl.findAllByKeyword(keyword, title, pageable).map(issue ->
                issue.toDto(likesService.getIssueLikes(issue.getId()),
                    usersRepository.findById(issue.getUsers().getId()).orElseThrow(() ->
                            new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")).toIssueDto()));
    }

    public Page<IssueDTO.ResponseDTO> search(String title, String category) {
        List<String> categories = new ArrayList<>();
        if(category != null){
            categories = Arrays.asList(category.split(","));
        }

        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));
        List<IssueDTO.ResponseDTO> issuePageList = communityRepositoryImpl.findAllByCategory(title, categories, pageable).map(issue ->
                issue.toDto(likesService.getIssueLikes(issue.getId()),
                        usersRepository.findById(issue.getUsers().getId()).orElseThrow(() ->
                                new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")).toIssueDto())).toList();
        // TODO pageImpl 하나만 사용해서 구현하기 현재 repo안에서도 사용중
        return new PageImpl<>(issuePageList, pageable, issuePageList.size());
    }

    @Transactional
    public IssueDTO.ResponseDTO detailSolvedIssue(long issueId) {
        // 이슈, 좋아요, 댓글
        Issue issueView = issueRepository.findById(issueId).orElseThrow(() -> new IllegalArgumentException("해당 이슈가 없습니다."));
        issueView.updateViews(); // 조회수 +1

        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));
        Optional<Issue> optIssue = issueRepository.findById(issueId);

        IssueDTO.ResponseDTO responseDTO = optIssue.
                map(issue -> {
                            try {
                                return issue.
                                        toDetailDto(
                                                likesService.getIssueLikes(issue.getId()),
                                                usersRepository.findById(issue.getUsers().getId()).orElseThrow(() ->
                                                        new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")).toIssueDto(),
                                                commentRepository.findAllByIssue_IdOrderByCreatedAtDesc(issueId, pageable).stream().map(Comment::toDto).toList(),
                                                getMediumApi(optIssue.get().getKeyword())
                                        );
                            } catch (IOException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                ).orElseThrow(() -> new IllegalArgumentException("없는 이슈번호입니다."));

        return responseDTO;
    }

    /**
     * medium api 연결 및 연관 게시물 3개
     *
     * @param query
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public List<MediumArticle> getMediumApi(String query) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://medium2.p.rapidapi.com/topfeeds/" + query + "/top_month?count=3"))
                .header("X-RapidAPI-Key", "37723b71d2mshc3577e9bb3b3dc3p11ce5djsn5fbb4f0acd78")
                .header("X-RapidAPI-Host", "medium2.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        List<String> relatedArticlesId = null;
        List<MediumArticle> articleList = new ArrayList<>();

        try {
            obj = (JSONObject) parser.parse(response.body());
            relatedArticlesId = Arrays.stream(obj.get("topfeeds").toString().split("\"")).toList();
            for (String id : relatedArticlesId) {
                if (id.length() > 3) {
                    articleList.add(getArticlesInfo(id));
                }
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        log.info("####### : " + articleList);

        return articleList;
    }


    public MediumArticle getArticlesInfo(String articleId) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://medium2.p.rapidapi.com/article/" + articleId))
                .header("X-RapidAPI-Key", "37723b71d2mshc3577e9bb3b3dc3p11ce5djsn5fbb4f0acd78")
                .header("X-RapidAPI-Host", "medium2.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        MediumArticle article = null;

        try {
            JsonNode jsonNode = objectMapper.readTree(response.body());
            article = new MediumArticle(jsonNode.get("title").asText(), jsonNode.get("subtitle").asText(), jsonNode.get("image_url").asText(), jsonNode.get("url").asText());
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        }

        return article;
    }


}
