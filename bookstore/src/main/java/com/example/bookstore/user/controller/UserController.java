package com.example.bookstore.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.bookstore.common.Result;
import com.example.bookstore.user.entity.User;
import com.example.bookstore.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    // 现在注入 Service
    private final UserService userService;

    // 查询所有用户
    @GetMapping("/list")
    public Result<List<User>> list() {
        return Result.success(userService.list());
    }

    /**
     * 分页查询用户
     * GET /user/page?pageNum=1&pageSize=10
     */
    @GetMapping("/page")
    public Result<Map<String, Object>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        // 1. 构建分页对象
        Page<User> page = new Page<>(pageNum, pageSize);

        // 2. 执行分页查询，参数是 null 表示查询所有
        userService.page(page, null);

        // 自定义返回格式
        Map<String, Object> map = new HashMap<>();
        map.put("list", page.getRecords());    // 列表
        map.put("total", page.getTotal());    // 总条数
        map.put("current", page.getCurrent());// 当前页
        map.put("size", page.getSize());      // 页长
        map.put("pages", page.getPages());      // 页长

        return Result.success(map);
    }

    // 根据ID查询
    @GetMapping("/get")
    public Result<User> get(@RequestParam Integer id) {
        if(userService.getById(id) == null) {
            return Result.error("用户不存在！");
        } else {
            return Result.success(userService.getById(id));
        }
    }

    // 登录
    @PostMapping("/login")
    public Result<User> login(@RequestBody User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());

        User dbUser = userService.getOne(wrapper);
        if (dbUser == null) {
            return Result.error("用户不存在");
        }
        if (!dbUser.getPassword().equals(user.getPassword())) {
            return Result.error("密码错误");
        }
        return Result.success(dbUser);
    }

    // 新增用户
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody User user) {
        user.setId(null); // 强制清空ID
        // 1. 先校验 用户名 是否重复
        LambdaQueryWrapper<User> queryUsername = new LambdaQueryWrapper<>();
        queryUsername.eq(User::getUsername, user.getUsername());
        if (userService.getOne(queryUsername) != null) {
            return Result.error("用户名已存在！");
        }

        // 2. 再校验 手机号 是否重复
        LambdaQueryWrapper<User> queryPhone = new LambdaQueryWrapper<>();
        queryPhone.eq(User::getPhone, user.getPhone());
        if (userService.getOne(queryPhone) != null) {
            return Result.error("手机号已存在！");
        }

        // 3. 都不重复才保存
        boolean save = userService.save(user);
        return save ? Result.success() : Result.error("添加失败");
    }

    // 修改用户
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody User user) {
        return Result.success(userService.updateById(user));
    }

    // 删除用户
    @PostMapping("/delete")
    public Result<Boolean> delete(@RequestBody User user) {
        return Result.success(userService.removeById(user.getId()));
    }
}