package com.study.bang.gateway.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

// 사전(요청) 필터 -> 대상서비스 -> 사후(응답) 필터
// 8.7 사후 필터
@Configuration
@Slf4j
@RequiredArgsConstructor
public class ResponseFilter {

    private final FilterUtils filterUtils;

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                String correlationId = filterUtils.getCorrelationId(requestHeaders);
                log.debug("Adding the correlation id to the outbound headers. {}", correlationId);
                exchange.getResponse().getHeaders().add(FilterUtils.CORRELATION_ID, correlationId);
                log.debug("Completing outgoing request for {}.", exchange.getRequest().getURI());
            }));
        };
    }
}
