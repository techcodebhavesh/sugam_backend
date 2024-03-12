package com.ankush.test.Authenication.Filters;


import com.ankush.test.Authenication.AuthenticationService.AuthenticationManager;
import com.ankush.test.Authenication.Model.Login;
import com.ankush.test.Utils.Database.DatabaseService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AuthenticationFilter implements Filter {


    @Autowired
    AuthenticationManager manager;
//    List<String> paths= List.of("/AISSMS/api");

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        try {
            manager = new AuthenticationManager(new DatabaseService());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.printf("Processing %s\n", ((HttpServletRequest) req).getRequestURI());
        System.out.println(Arrays.toString(((HttpServletRequest) req).getCookies()));
        Optional<Login> login = manager.CheckAuthenticatedFromCookie((HttpServletRequest) req);
        System.out.println(login);
        if (login.isPresent()) {
            //AUTHENTICATED
            Login l = login.get();
            System.out.println("LOGGED IN:"+l.getStatus());
            filterChain.doFilter(req, res);
        } else {
            System.out.println("FAILED");
            res.getWriter().println("ACCESS DENIED");
            ((HttpServletResponse) res).setStatus(403);

        }
    }
}
