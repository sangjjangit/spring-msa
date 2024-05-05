package com.study.bang.license.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 클라이언트 요청 -> 서비스 게이트웨이
// -> 라이선싱 서비스[UserContextFilter(헤더정보셋팅)->비즈니스로직->RestTemplate 인터셉터(헤더셋팅)->조직서비스호출]->조직서비스
@Component
@Slf4j
public class UserContextFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        UserContextHolder.getContext().setCorrelationId(httpServletRequest.getHeader(UserContext.CORRELATION_ID) );
        UserContextHolder.getContext().setUserId(httpServletRequest.getHeader(UserContext.USER_ID));
        UserContextHolder.getContext().setAuthToken(httpServletRequest.getHeader(UserContext.AUTH_TOKEN));
        UserContextHolder.getContext().setOrganizationId(httpServletRequest.getHeader(UserContext.ORGANIZATION_ID));

        log.debug("UserContextFilter Correlation id: {}", UserContextHolder.getContext().getCorrelationId());

        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    @Override
    public void destroy() {}
}
