package com.example.bookstore.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.bookstore.common.Result;
import com.example.bookstore.entity.User;
import com.example.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
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
        return Result.success(userService.getById(id));
    }

    // 登录
    @PostMapping("/login")
    public Result<User> login(@RequestBody User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());

        User dbUser = userService.getOne(wrapper);
        if (dbUser == null) {
            return Result.fail("用户不存在");
        }
        if (!dbUser.getPassword().equals(user.getPassword())) {
            return Result.fail("密码错误");
        }
        return Result.success(dbUser);
    }

    // 新增用户
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody User user) {
        return Result.success(userService.save(user));
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