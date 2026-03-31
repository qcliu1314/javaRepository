package com.example.bookstore.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

// 注解：自动生成 get/set/toString 等方法
@Data
// 注解：对应数据库里的 user 表
@TableName("user")
public class User {

    // 主键 ID，自增
    @TableId(type = IdType.AUTO)
    private Long id;

    // 账号
    private String username;

    // 密码
    private String password;

    // 昵称
    private String nickname;

    // 创建时间
    private LocalDateTime createTime;
}