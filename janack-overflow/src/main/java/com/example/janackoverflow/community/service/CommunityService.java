package com.example.janackoverflow.community.service;

import com.example.janackoverflow.community.domain.MediumArticle;
import com.example.janackoverflow.community.entity.Comment;
import com.example.janackoverflow.community.repository.CommentRepository;
import com.example.janackoverflow.community.repository.CommunityRepositoryImpl;
import com.example.janackoverflow.issue.domain.IssueDTO;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.issue.repository.IssueRepository;
import com.example.janackoverflow.issue.service.SolutionService;
import com.example.janackoverflow.user.repository.UsersRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class CommunityService {
    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final UsersRepository usersRepository;
    private final LikesService likesService;

    private final SolutionService solutionService;

    Map<Long, List<MediumArticle>> articleMap = new HashMap<>();
    List<MediumArticle> mediumArticleList = new ArrayList<>();

    @Value("${external.medium.medium-key}")
    private String apikey;
    private final CommunityRepositoryImpl communityRepositoryImpl;

    public CommunityService (CommentRepository commentRepository,
                             IssueRepository issueRepository,
                             LikesService likesService,
                             UsersRepository usersRepository,
                             SolutionService solutionService,
                             CommunityRepositoryImpl communityRepositoryImpl) {

        this.commentRepository = commentRepository;
        this.issueRepository = issueRepository;
        this.likesService = likesService;
        this.usersRepository = usersRepository;
        this.solutionService = solutionService;
        this.communityRepositoryImpl = communityRepositoryImpl;
    }

    public Page<IssueDTO.ResponseDTO> getSolvedIssueList(String order, int pageNo) {
        log.info("!!!!!!!title : " + " pageNo : " + pageNo);
        Pageable pageable = null;

        pageable = PageRequest.of(pageNo, 10, Sort.by(Sort.Direction.DESC, Objects.requireNonNullElse(order, "id")));
        // 페이져블 객체와 errorId로 모든 comment를 들고온다.
        Page<Issue> issueList = issueRepository.findByPublicStatusAndStatusOrderByCreatedAtDesc(true, "03", pageable);
        // 댓글DtoList
        List<IssueDTO.ResponseDTO> issueResponseDtoList = issueList.stream().map(issue -> issue.toDto(likesService.getIssueLikes(issue.getId()), usersRepository.findById(issue.getUsers().getId()).get().toIssueDto())).toList();

        Page<IssueDTO.ResponseDTO> issuePage = new PageImpl<>(issueResponseDtoList, pageable, issueList.getTotalElements());

//        log.info("get Content : " + issuePage.getContent().get(0));
        log.info("page : " + issuePage.getTotalPages());
        log.info("total : " + issuePage.getTotalElements());

        return issuePage;
    }

    public Page<IssueDTO.ResponseDTO> getIssueBySelectedCategory(String category, int pageNo) {

        Pageable pageable = PageRequest.of(pageNo, 10, Sort.by(Sort.Direction.DESC, "id"));
        Page<Issue> issueList =communityRepositoryImpl.findAllByCategoryName(category, pageable);
        List<IssueDTO.ResponseDTO> issuePageList = issueList.map(issue ->
                issue.toDto(likesService.getIssueLikes(issue.getId()),
                        usersRepository.findById(issue.getUsers().getId()).orElseThrow(() ->
                                new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")).toIssueDto())).toList();

        // TODO pageImpl 하나만 사용해서 구현하기 현재 repo안에서도 사용중
        return new PageImpl<>(issuePageList, pageable, issueList.getTotalElements());
    }

    public Page<IssueDTO.ResponseDTO> searchKeyword(String title) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));
        return communityRepositoryImpl.findAllByTitle(title, pageable).map(issue ->
                issue.toDto(likesService.getIssueLikes(issue.getId()),
                    usersRepository.findById(issue.getUsers().getId()).orElseThrow(() ->
                            new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")).toIssueDto()));
    }

    public Page<IssueDTO.ResponseDTO> search(String title, String category, int pageNo) {
        log.info("!!!!!!!title : " + title + "category : " + category + " pageNo : " + pageNo);
        log.info("title isEmpty : "+ title.isEmpty());
        log.info("category : " + category + (category == null));
        if(title.isEmpty() && category.isEmpty()) {
            log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            return getSolvedIssueList(null, pageNo);
        }
        List<String> categories = new ArrayList<>();
        if(category != null){
            categories = Arrays.asList(category.split(","));
        }
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.by(Sort.Direction.DESC, "id"));
        Page<Issue> issueList =communityRepositoryImpl.findAllByCategory(title, categories, pageable);
        List<IssueDTO.ResponseDTO> issuePageList = issueList.map(issue ->
                issue.toDto(likesService.getIssueLikes(issue.getId()),
                        usersRepository.findById(issue.getUsers().getId()).orElseThrow(() ->
                                new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")).toIssueDto())).toList();

        log.info("issuePageList.size() :" + issuePageList.size());
        // TODO pageImpl 하나만 사용해서 구현하기 현재 repo안에서도 사용중
        return new PageImpl<>(issuePageList, pageable, issueList.getTotalElements());
    }

    @Transactional
    public IssueDTO.ResponseDTO detailSolvedIssue(long issueId) {
        // 이슈, 좋아요, 댓글
        Issue issueView = issueRepository.findById(issueId).orElseThrow(() -> new IllegalArgumentException("해당 이슈가 없습니다."));
        issueView.updateViews(); // 조회수 +1

        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));
        Optional<Issue> optIssue = issueRepository.findById(issueId);

        // TODO map 에 있는지 없는지 확인
        if( articleMap.get(issueId) == null ) {
            try {
                mediumArticleList = getMediumApi(optIssue.orElseThrow(() -> new IllegalArgumentException("해당 이슈를 찾을 수 없습니다.")).getKeyword());
                articleMap.put(issueId, mediumArticleList);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // TODO solutionDTO 추가
        IssueDTO.ResponseDTO responseDTO = optIssue.
                map(issue -> issue.
                        toDetailDto(
                                likesService.getIssueLikes(issue.getId()),
                                usersRepository.findById(issue.getUsers().getId()).orElseThrow(() ->
                                        new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")).toIssueDto(),
                                commentRepository.findAllByIssue_IdOrderByCreatedAtDesc(issueId, pageable).stream().map(Comment::toDto).toList(),
                                articleMap.get(issueId), solutionService.getSolution(issueId)
                        )
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
        log.info("@@@@@@@@@@ + " + apikey);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://medium2.p.rapidapi.com/search/articles?query=" + query.replaceAll(",", "")))
                .header("X-RapidAPI-Key", apikey)
                .header("X-RapidAPI-Host", "medium2.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//        log.info("@@@@@@@@@@@ body : " + response.body());
        log.info("@@@@@@@@@@@ query : " +  query.replaceAll(",", ""));
        JSONParser parser = new JSONParser();
        JSONObject obj;
        List<String> relatedArticlesId = null;
        List<MediumArticle> articleList = new ArrayList<>();

        try {
            obj = (JSONObject) parser.parse(response.body());
            int articleSize = 0;

            JSONArray articlesObj = (JSONArray) obj.get("articles");
            log.info("articlesObj : "+ articlesObj.size());
            articleSize = articlesObj.size();
            if(articleSize > 3) {
                articleSize = 3;

            } else if(articlesObj.isEmpty()) {
                log.info("비어있음");
            }

            for (int i = 0 ; i < articleSize ; i ++ ) {
                if (articlesObj.get(i) != null) {
                    System.out.println(articlesObj.get(i).toString());
                    articleList.add(getArticlesInfo(articlesObj.get(i).toString()));
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
                .header("X-RapidAPI-Key", apikey)
                .header("X-RapidAPI-Host", "medium2.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        MediumArticle article = null;

        try {
            JsonNode jsonNode = objectMapper.readTree(response.body());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            article = new MediumArticle(jsonNode.get("title").asText(), jsonNode.get("subtitle").asText(), jsonNode.get("image_url").asText(), jsonNode.get("url").asText(), jsonNode.get("claps").asInt(), LocalDateTime.from(formatter.parse(jsonNode.get("published_at").asText())));

        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        }

        return article;
    }


}
