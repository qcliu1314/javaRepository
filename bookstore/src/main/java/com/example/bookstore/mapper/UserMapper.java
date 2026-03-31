package com.example.bookstore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bookstore.entity.User;
import org.apache.ibatis.annotations.Mapper; // 必须是这个包！

@Mapper
public interface UserMapper extends BaseMapper<User> {
}