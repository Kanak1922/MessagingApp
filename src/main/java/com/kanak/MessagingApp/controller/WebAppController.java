package com.kanak.MessagingApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebAppController {

    @GetMapping("/http/{path:[^\\.]*}")
    public String forward() {
        return "forward:/";
    }
}
