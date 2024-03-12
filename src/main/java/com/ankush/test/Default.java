package com.ankush.test;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class Default {

    @GetMapping()
    public String HelloWorld(HttpServletRequest req) {
//        System.out.println(manager.getLoginFromSession("1"));
        System.out.println(Arrays.toString(req.getCookies()));
        return "Hello World";
    }
    @GetMapping("/AISSMS/common/hello")
    public String getHello(){
        return "Hello World";
    }


}
