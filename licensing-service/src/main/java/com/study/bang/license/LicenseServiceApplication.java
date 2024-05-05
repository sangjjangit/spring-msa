package com.study.bang.license;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RefreshScope
@EnableDiscoveryClient // 1. Discovery Client 로 서비스 인스턴스 검색 방법
@EnableFeignClients // 3. Feign 클라이언트로 서비스 호출(RestTemplate 의 대안)
public class LicenseServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LicenseServiceApplication.class, args);
    }

    // 2. 로드 밸런서를 지원하는 스프링 REST 템플릿으로 서비스 호출
    @LoadBalanced // 모든 인스턴스를 얻는다.
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}
