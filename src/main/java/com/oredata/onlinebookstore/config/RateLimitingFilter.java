package com.oredata.onlinebookstore.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class RateLimitingFilter implements Filter {

    private final Cache<String, Integer> requestCountsPerIpAddress = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build();

    private static final int MAX_REQUESTS_PER_HOUR = 1000; // örnek limit değeri

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String clientIpAddress = getClientIP((HttpServletRequest) request);

        Integer existingRequests = requestCountsPerIpAddress.getIfPresent(clientIpAddress);
        if (existingRequests == null) {
            requestCountsPerIpAddress.put(clientIpAddress, 1);
        } else if (existingRequests >= MAX_REQUESTS_PER_HOUR) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            httpResponse.getWriter().write("Too many requests");
            return;
        } else {
            requestCountsPerIpAddress.put(clientIpAddress, existingRequests + 1);
        }

        chain.doFilter(request, response);
    }

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}
}
