package com.user.registration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    //Handles the web requests
    //This is a view controller, it returns the which UI page needs to resolve by thymeleaf
    @GetMapping("/")
    public String getHomePage(){

        return "index";
    }

    @GetMapping("/login")
    public  String getLoginPage(){

        return "index";
    }
}
