package com.dattb.info.profile.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.dattb.info.profile.util.Constant.TRACE_ID;
import static com.dattb.info.profile.util.Constant.X_REQUEST_ID;


@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String requestId = request.getHeader(X_REQUEST_ID);

        long startTime = System.currentTimeMillis();
        if (StringUtils.hasLength(requestId))
            MDC.put(TRACE_ID, requestId);
        else MDC.put(TRACE_ID, String.valueOf(startTime));

        log.info("REQUEST DATA: {} {}", request.getMethod(), requestURI);
        filterChain.doFilter(request, response);

        long endTime = System.currentTimeMillis();
        log.info("FINISHED PROCESSING: TIME TAKEN={} ms; RESPONSE CODE={}", endTime - startTime, response.getStatus());

    }
}
