package com.example.multiDataSource.service;

import com.example.multiDataSource.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    boolean insertUser(User user);
    List<User> queryAllUser();

}
