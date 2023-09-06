package com.dattb.info.profilebe.filter;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.dattb.info.profilebe.util.Constant.TRACE_ID;
import static com.dattb.info.profilebe.util.Constant.X_REQUEST_ID;


@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String requestId = request.getHeader(X_REQUEST_ID);

        long startTime = System.currentTimeMillis();
        if (StringUtils.isNotBlank(requestId))
            MDC.put(TRACE_ID, requestId);
        else MDC.put(TRACE_ID, String.valueOf(startTime));

        log.info("REQUEST DATA: {} {}", request.getMethod(), requestURI);
        filterChain.doFilter(request, response);

        long endTime = System.currentTimeMillis();
        log.info("FINISHED PROCESSING: TIME TAKEN={} ms; RESPONSE CODE={}", endTime - startTime, response.getStatus());

    }
}
