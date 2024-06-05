package com.odop.community.controller;

import com.odop.community.dto.User;
import org.springframework.http.ResponseEntity;

public interface UserController {
    ResponseEntity<Void> join(User user);
}
