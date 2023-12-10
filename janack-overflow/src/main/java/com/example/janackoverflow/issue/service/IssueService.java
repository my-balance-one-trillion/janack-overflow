package com.example.janackoverflow.issue.service;

import com.example.janackoverflow.issue.domain.IssueDTO;
import com.example.janackoverflow.issue.domain.request.CreateIssueRequestDTO;
import com.example.janackoverflow.issue.domain.response.IssueResponseDTO;
import com.example.janackoverflow.issue.domain.response.StackOverflowResponse;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.issue.repository.IssueRepository;
import com.example.janackoverflow.user.entity.Users;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IssueService {
    private final IssueRepository issueRepository;

    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public Issue getIssue(Long issueId) {
        return issueRepository.findById(issueId).get();
    }

    // 에러 등록
    @Transactional
    public Issue createIssue(CreateIssueRequestDTO issueRequestDTO, Users users) {
        return issueRepository.save(issueRequestDTO.toEntity(users));
    }

    // 에러 조회
    @Transactional(readOnly = true)
    public Optional<IssueResponseDTO> getIssueById(Long issueId){
        Optional<Issue> optionalIssue = issueRepository.findById(issueId);

        if(optionalIssue.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "에러 없음");
        }

        return optionalIssue.map(IssueResponseDTO::toDto);
    }

    // 에러 등록 시간 조회
    @Transactional(readOnly = true)
    public LocalDateTime getIssueCreatedAt(Long issueId) {
        Optional<Issue> createAt = issueRepository.findById(issueId);
        return createAt.map(Issue::getCreatedAt).orElse(null);
    }

    // 에러 키워드로 검색
    @Transactional(readOnly = true)
    public List<StackOverflowResponse> searchByKeyword(Long issueId) throws JsonProcessingException {
        Optional<Issue> issueOptional = issueRepository.findById(issueId);

        if (issueOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Issue issue = issueOptional.get();
        String keyword = issue.getKeyword();

        // StackOverflow API 검색 Endpoint URL
        String apiUrl = "https://api.stackexchange.com/2.3/search?pagesize=3&order=desc&sort=votes&intitle=" + keyword + "&site=stackoverflow";

        // WebClient
        WebClient webClient = WebClient.create();
        String response = webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // 검색 결과
        List<StackOverflowResponse> searchResult = new ArrayList<>();
        if(response != null){
            // JSON 데이터 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            // 응답으로 받은 JSON 문자열을 JsonNode로 파싱
            JsonNode responseNode = objectMapper.readTree(response);

            JsonNode itemsNode = responseNode.get("items");  // "items" 키에 해당하는 값 가져오기
            for(JsonNode itemNode : itemsNode){  // 각 항목에서 "tags","link","title" 키에 해당하는 값 가져오기
                JsonNode tagsNode = itemNode.get("tags");
                JsonNode linksNode = itemNode.get("link");
                JsonNode titlesNode = itemNode.get("title");

                StackOverflowResponse item = getStackOverflowResponse(tagsNode, linksNode, titlesNode); // 가져온 값을 사용해서 객체 생성
                searchResult.add(item);  // 생성한 객체를 검색 결과 리스트에 추가
            }
        }
        return searchResult;
    }

    // 태그, 링크, 제목 값을 사용해서 StackOverflowResponse 객체 생성
    private static StackOverflowResponse getStackOverflowResponse(JsonNode tagsNode, JsonNode linksNode, JsonNode titlesNode) {
        List<String> tagsList = new ArrayList<>();
        if(tagsNode != null){
            for(JsonNode tagNode : tagsNode){
                tagsList.add(String.valueOf(tagNode).replace("\"","")); // 이스케이프 문자(JSON 데이터를 파싱할 때 사용) 제거
            }
        }

        String link = String.valueOf(linksNode).replace("\"","");
        String title = String.valueOf(titlesNode).replace("\"","");

        StackOverflowResponse item = new StackOverflowResponse();
        item.setTags(tagsList);
        item.setLink(link);
        item.setTitle(title);
        return item;
    }

    // 에러 상태 변경 (포기)
    @Transactional
    public Issue updateIssueStatus(Long issueId) {
        Optional<Issue> optionalIssue = issueRepository.findById(issueId);
        if(optionalIssue.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "에러 없음");
        }
        Issue issue = optionalIssue.get();
        issue.setStatus("02");
        return issueRepository.save(issue);
    }
}
