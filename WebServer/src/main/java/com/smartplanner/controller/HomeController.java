package com.smartplanner.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HomeController {

    @RequestMapping("/")
    public String getHomeControllerString() {
        return "Smart Planner API";
    }
}
