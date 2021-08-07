package com.example.multiDataSource.controller;

import com.example.multiDataSource.configuration.TargetDataSource;
import com.example.multiDataSource.model.User;
import com.example.multiDataSource.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @TargetDataSource("slave")
    @GetMapping("/queryAll")
    public List<User> getAllMasterProduct() throws Exception {
        return userService.queryAllUser();
    }
}
