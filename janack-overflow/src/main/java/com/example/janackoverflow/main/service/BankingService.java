package com.example.janackoverflow.main.service;

import com.example.janackoverflow.main.domain.ApiHeader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import javax.print.DocFlavor;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
@Service
public class BankingService {

    @Autowired
    private RestTemplate restTemplate;

    //공통 데이터
    private static final String baseURL = "https://developers.nonghyup.com/";

    @Value("${external.api.iscd}")
    private String iscd;
    @Value("${external.api.access-token}")
    private String accessToken;
    @Value("${external.api.output-acnt-num}")
    private String acntNum;
    @Value("${external.api.fin-acno}")
    private String finAcno;

//    계좌 생성
    public String createAccount(Long id) throws JsonProcessingException {
        return ""+id+3020000009687L;
    }
    //계좌 이체
    //에러 해결 시 적금
    public void transfer(int amount, String acntNum) throws JsonProcessingException {

            String apiNm = "ReceivedTransferAccountNumber";
            List<String> timeList = createTime();
            ApiHeader apiHeader = new ApiHeader(iscd, accessToken, apiNm, timeList.get(0), timeList.get(1), createIsTuno());

            ObjectMapper mapper = new ObjectMapper();
            String headerJson = mapper.writeValueAsString(apiHeader);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            JSONObject request = new JSONObject()
                    .put("Header", new JSONObject(headerJson))
                    .put("Acno", acntNum)
                    .put("Bncd", "011")
                    .put("Tram", Integer.toString(amount))
                    .put("DractOtlt", "출금테스트")
                    .put("MractOtlt", "입금테스트");
            HttpEntity<String> httpEntity = new HttpEntity<>(request.toString(), httpHeaders);
            System.out.println("httpEntity:" + httpEntity);
            ResponseEntity<String> response = restTemplate.exchange(baseURL + apiNm + ".nh", HttpMethod.POST, httpEntity, String.class);
            JSONObject jsonObject = new JSONObject(response.getBody());
            System.out.println(jsonObject.toString());


    }


//    거래 고유번호 생성
    public String createIsTuno(){
        return Long.toString(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }
    //거래 시간 생성
    public List<String> createTime(){
        LocalDateTime localDateTime = LocalDateTime.now();

        List<String> timeList = new ArrayList<>();

        timeList.add(localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        timeList.add(localDateTime.format(DateTimeFormatter.ofPattern("HHmmss")));

        return timeList;
    }
}
