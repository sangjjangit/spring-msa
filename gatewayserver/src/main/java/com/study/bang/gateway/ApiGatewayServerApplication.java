package com.study.bang.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableEurekaClient - 2022.0 이상부터 application.yml 에 spring.application.name 이 있으면 Eureka 등록되는 형식으로 변경됨.
public class ApiGatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayServerApplication.class, args);
    }
}
