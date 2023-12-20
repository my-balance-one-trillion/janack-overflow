package com.example.janackoverflow.main.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class ApiHeader {
    @JsonProperty("ApiNm")
    String apiNm;

    @JsonProperty("Tsymd")
    String tsymd;//YYYYMMDD

    @JsonProperty("Trtm")
    String trtm;//HHMMSS

    @JsonProperty("Iscd")
    String iscd;

    @JsonProperty("FintechApsno")
    String fintechApsno;//001

    @JsonProperty("ApiSvcCd")
    String apiSvcCd;//아무값 넣어도 됨

    @JsonProperty("IsTuno")
    String isTuno;//중복X

    @JsonProperty("AccessToken")
    String accessToken;
    //공통되는 값들
    public ApiHeader(String iscd, String accessToken, String apiNm, String tsymd, String trtm, String isTuno){

        this.iscd = iscd;
        fintechApsno = "001";
        apiSvcCd = "DrawingTransferA";
        this.accessToken = accessToken;
        this.apiNm = apiNm;
        this.tsymd = tsymd;
        this.trtm = trtm;
        this.isTuno = isTuno;
    }
}
