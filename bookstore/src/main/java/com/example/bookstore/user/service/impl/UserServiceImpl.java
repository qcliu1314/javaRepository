package com.example.bookstore.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bookstore.user.entity.User;
import com.example.bookstore.user.mapper.UserMapper;
import com.example.bookstore.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}