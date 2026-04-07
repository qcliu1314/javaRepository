package com.example.bookstore.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bookstore.user.entity.User;
import org.apache.ibatis.annotations.Mapper; // 必须是这个包！

@Mapper
public interface UserMapper extends BaseMapper<User> {
}