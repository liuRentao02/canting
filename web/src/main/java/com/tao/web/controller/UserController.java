package com.tao.web.controller;

import com.tao.service.UserService;
import com.tao.utils.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * UserController
 *
 * @author LiuRentao
 * @version 1.0
 * @since 2025/9/16 22:25
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping()
    public Result<String> index() {
        return userService.login();
    }
}
