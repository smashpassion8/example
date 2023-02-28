package com.javachinna.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @Autowired
    Environment env;

    @GetMapping("/profile")
    public String[] getCurrentProfile(){
        return env.getActiveProfiles();
    }
}
