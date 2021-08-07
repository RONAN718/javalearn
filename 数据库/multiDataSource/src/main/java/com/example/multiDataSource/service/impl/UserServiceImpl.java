package com.example.multiDataSource.service.impl;

import com.example.multiDataSource.mapper.UserMapper;
import com.example.multiDataSource.model.User;
import com.example.multiDataSource.model.UserExample;
import com.example.multiDataSource.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public boolean insertUser(User user) {
        int result=0;
        result=userMapper.insert(user);
        return result==1;
    }

    @Override
    public List<User> queryAllUser() {
        List<User> users=new ArrayList<>();
        UserExample userExample=new UserExample();
        UserExample.Criteria c=userExample.createCriteria();
        c.andIdEqualTo(1);
        users=userMapper.selectByExample(userExample);
        return users;
    }
}
