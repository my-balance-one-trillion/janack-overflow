package com.example.janackoverflow.issue.service;

import com.example.janackoverflow.global.exception.BusinessLogicException;
import com.example.janackoverflow.global.exception.ExceptionCode;
import com.example.janackoverflow.issue.domain.request.CreateIssueRequestDTO;
import com.example.janackoverflow.issue.domain.response.IssueResponseDTO;
import com.example.janackoverflow.issue.domain.response.SolutionResponseDTO;
import com.example.janackoverflow.issue.domain.response.StackOverflowResponse;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.issue.repository.IssueRepository;
import com.example.janackoverflow.saving.domain.response.MonthlyAmountDTO;
import com.example.janackoverflow.saving.entity.InputAccount;
import com.example.janackoverflow.saving.repository.InputAccountRepository;
import com.example.janackoverflow.user.entity.Users;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static com.example.janackoverflow.issue.entity.QIssue.issue;

@Service
public class IssueService {
    private final IssueRepository issueRepository;
    private final InputAccountRepository inputAccountRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public IssueService(IssueRepository issueRepository, InputAccountRepository inputAccountRepository, JPAQueryFactory jpaQueryFactory) {
        this.issueRepository = issueRepository;
        this.inputAccountRepository = inputAccountRepository;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    // 에러 등록
    @Transactional
    public Issue createIssue(CreateIssueRequestDTO issueRequestDTO, Users users) {
        InputAccount inprogressAccount = inputAccountRepository.findByUsersIdAndStatus(users.getId(),"01")  // 현재 진행 중인 계좌 조회
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ACCOUNT_NOT_FOUND));
        if(issueRepository.findByUsersIdAndStatus(users.getId(),"01").isPresent()){  // 현재 진행 중인 에러가 있을 경우
            throw new BusinessLogicException(ExceptionCode.ERROR_EXIST);
        }
        return issueRepository.save(issueRequestDTO.toEntity(users, inprogressAccount));
    }

    // 현재 진행 중인 에러 조회
    @Transactional(readOnly = true)
    public IssueResponseDTO getIssueByUserId(Users users){
        inputAccountRepository.findByUsersIdAndStatus(users.getId(),"01")  // 현재 진행 중인 계좌 조회
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ACCOUNT_NOT_FOUND));

        Issue inprogressIssue = issueRepository.findByUsersIdAndStatus(users.getId(),"01")
                .orElse(null);

        if (inprogressIssue == null) {
            return null;
        }
       return IssueResponseDTO.toDto(inprogressIssue);
    }

    // 에러 키워드로 검색
    @Transactional(readOnly = true)
    public List<StackOverflowResponse> searchByKeyword(Long issueId) throws JsonProcessingException {
        Issue issue =  issueRepository.findById(issueId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ERROR_NOT_FOUND));
        String keyword = issue.getKeyword();
        if (keyword.isEmpty()) {
            return null;
        }

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

    private static StackOverflowResponse getStackOverflowResponse(JsonNode tagsNode, JsonNode linksNode, JsonNode titlesNode) {
        List<String> tags = new ArrayList<>();
        if(tagsNode != null){
            for(JsonNode tagNode : tagsNode){
                tags.add(String.valueOf(tagNode).replace("\"","")); // 이스케이프 문자(JSON 데이터를 파싱할 때 사용) 제거
            }
        }

        String link = String.valueOf(linksNode).replace("\"","");
        String title = String.valueOf(titlesNode).replace("\"","");

        return new StackOverflowResponse(tags, link, title);
    }

    // 에러 상태 변경 (포기)
    @Transactional
    public Issue updateIssueStatus(Long issueId) {
        Issue issue =  issueRepository.findById(issueId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ERROR_NOT_FOUND));
        issue.updateStatus("02");
        return issueRepository.save(issue);
    }

    // 해결한 에러 조회
    @Transactional(readOnly = true)
    public List<IssueResponseDTO> getSolvedIssuesByUserId(Users users) {
        return issueRepository.findByUsers_IdAndStatus(users.getId(),"03")
                .stream().map(IssueResponseDTO::toDto)
                .toList();
    }

    // 에러 조회
    @Transactional(readOnly = true)
    public IssueResponseDTO getIssue(Long issueId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ERROR_NOT_FOUND));;
        return IssueResponseDTO.toDto(issue);
    }

    // 년도월에 해당하는 해결한 에러 조회
    @Transactional(readOnly = true)
    public List<IssueResponseDTO> getMonthlyIssues(List<SolutionResponseDTO> monthlySolutions) {
        List<Long> issueIds = new ArrayList<>();
        for (SolutionResponseDTO solution : monthlySolutions) {
            issueIds.add(solution.getIssueId());
        }
        return issueRepository.findAllById(issueIds)
                .stream().map(IssueResponseDTO::toDto)
                .toList();
    }

    // 누적 금액 계산
    public List<MonthlyAmountDTO> getMonthlyAmount(Users users) {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime fiveMonthsAgo = currentDate.minusMonths(5);

        return jpaQueryFactory
                .select(Projections.fields(MonthlyAmountDTO.class,
                        issue.createdAt.year().as("year"),
                        issue.createdAt.month().as("month"),
                        issue.amount.sum().coalesce(0).as("amount")))
                .from(issue)
                .where(issue.users.eq(users).and(issue.status.eq("03")))
                .where(issue.createdAt.between(fiveMonthsAgo, currentDate))
                .groupBy(issue.createdAt.year(), issue.createdAt.month())
                .fetch();
    }
}
