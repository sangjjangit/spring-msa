package com.study.bang.gateway.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

// 사전(요청) 필터 -> 대상서비스 -> 응답 필터
// 8.5 사전 필터
// tmx-correlation-id 헤더는 사용자 요청을 추척하는 고유한 GUID 가 포함한다.
// tmx-correlation-id 헤더라는 게 있나????????
@Component
@Order(1)
@Slf4j
@RequiredArgsConstructor
public class TrackingFilter implements GlobalFilter {

    private final FilterUtils filterUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
        if (isCorrelationIdPresent(requestHeaders)) {
            log.debug("tmx-correlation-id found in tracking filter: {}. ",
                    filterUtils.getCorrelationId(requestHeaders));
        } else {
            String correlationID = generateCorrelationId();
            exchange = filterUtils.setCorrelationId(exchange, correlationID);
            log.debug("tmx-correlation-id generated in tracking filter: {}.", correlationID);
        }

        System.out.println("The authentication name from the token is : " + getUsername(requestHeaders));



        return chain.filter(exchange);
    }

    private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
        if (filterUtils.getCorrelationId(requestHeaders) != null) {
            return true;
        } else {
            return false;
        }
    }

    private String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }

    private String getUsername(HttpHeaders requestHeaders){
        String username = "";
        if (filterUtils.getAuthToken(requestHeaders)!=null){
            String authToken = filterUtils.getAuthToken(requestHeaders).replace("Bearer ","");
            JSONObject jsonObj = decodeJWT(authToken);
            try {
                username = jsonObj.getString("preferred_username");
            }catch(Exception e) {log.debug(e.getMessage());}
        }
        return username;
    }


    private JSONObject decodeJWT(String JWTToken) {
        String[] split_string = JWTToken.split("\\.");
        String base64EncodedBody = split_string[1];
        Base64 base64Url = new Base64(true);
        String body = new String(base64Url.decode(base64EncodedBody));
        JSONObject jsonObj = new JSONObject(body);
        return jsonObj;
    }
}
