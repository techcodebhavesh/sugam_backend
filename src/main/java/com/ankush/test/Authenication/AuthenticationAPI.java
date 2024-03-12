package com.ankush.test.Authenication;

import com.ankush.test.Authenication.AuthenticationService.AuthenticationManager;
import com.ankush.test.Authenication.Model.Login;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import java.util.Arrays;
import java.util.Optional;


@RestController
public class AuthenticationAPI {
    @Autowired
    AuthenticationManager manager;

    @GetMapping("/AISSMS/api/authenticate")
    public boolean authenticate(HttpServletRequest request, HttpServletResponse response, @RequestParam String user, @RequestParam String pass) {
        Optional<Login> login = manager.CheckAuthenticatedFromCookie(request);
        if (login.isPresent()) {
            return true;
        }
        if (manager.authenticate(user, pass)) {
            //ADD COOKIE
            manager.registerSession(response, user);
            return true;
        }
        return false;
    }

    @GetMapping("/AISSMS/api/register")
    public boolean register(HttpServletRequest request, HttpServletResponse response, @RequestParam String user, @RequestParam String pass, @RequestParam String username, @RequestParam String status) {
//        UUID uuid=UUID.randomUUID();
        Optional<Login> login = manager.CheckAuthenticatedFromCookie(request);
        if (login.isPresent()) {
            return true;
        }

        if (manager.addLogin(user, username, status, pass)) {
            manager.registerSession(response, user);
            return true;
        }
        return false;
    }
    @GetMapping("/AISSMS/api/get")
    public Login get(HttpServletRequest request, HttpServletResponse response) {
//        UUID uuid=UUID.randomUUID();

        System.out.println("HELLO");
        Optional<Login> login = manager.CheckAuthenticatedFromCookie(request);
        System.out.println(Arrays.toString(request.getCookies()));
        System.out.println(login);
        return login.orElse(null);
    }

    @GetMapping("AISSMS/api/sayHello")
    public String sayHello(HttpServletRequest req){
        return Arrays.toString(req.getCookies());
//        return "Hello";
    }

}
