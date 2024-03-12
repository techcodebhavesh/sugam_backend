package com.ankush.test.Authenication.Filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(2)
public class LogFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        System.out.printf("[PROCESSING:\t%s]\n", ((HttpServletRequest) req).getRequestURI());
        filterChain.doFilter(req, res);

    }
}
