package com.odop.community.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/users")
public class UserController implements UserOperations {

    @PostMapping("/")
    public void save() {
        
    }
}
