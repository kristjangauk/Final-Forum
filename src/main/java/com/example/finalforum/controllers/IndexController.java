package com.example.finalforum.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String displayIndex() {
        return "index";
    }

    @GetMapping("/addtopic")
    public String addTopic() {
        return "/addtopic";
    }
}
