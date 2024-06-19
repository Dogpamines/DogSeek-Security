package com.dogpamines.dogseek.user.controller;

import com.dogpamines.dogseek.user.model.dto.UserDTO;
import com.dogpamines.dogseek.user.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;        // 생성자 주입으로 하기!!!

    @PostMapping("/signup")
    public String signup(@RequestBody UserDTO user) {
        return userService.signUp(user);
    }
}
