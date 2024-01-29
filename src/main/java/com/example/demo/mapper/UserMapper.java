package com.example.demo.mapper;

import com.example.demo.dto.UserInfo;
import com.example.demo.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserInfo userToUserInfo(User user) {
        return new UserInfo(user.getName());
    }

}
