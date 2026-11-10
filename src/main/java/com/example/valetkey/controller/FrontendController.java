package com.example.valetkey.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {
    @GetMapping({"/login", "/register", "/public/{token}"})
    public String frontend() {
        return "forward:/index.html";
    }
}
