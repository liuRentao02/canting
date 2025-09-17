package com.tao.web.controller;

import com.tao.dto.RegisterUser;
import com.tao.service.UserService;
import com.tao.utils.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author tao
 * @since 2025-09-17
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public Result<?> register(@RequestBody RegisterUser registerUser) {
        return userService.registerUser(registerUser);
    }
}
