package com.tao.web.controller;

import com.tao.dto.RegisterUser;
import com.tao.dto.UpdateUser;
import com.tao.service.UserService;
import com.tao.utils.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author tao
 * @since 2025-09-17
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public Result<?> register(@RequestBody RegisterUser registerUser) {
        return userService.registerUser(registerUser);
    }

    @PostMapping("/update")
    public Result<?> updateUser(@RequestBody UpdateUser user) {
        return userService.updateUser(user);
    }

    @GetMapping("/list")
    public Result<?> getUserList(@RequestParam("num") int num,
                                 @RequestParam("currentPage") int currentPage,
                                 @RequestParam("size") int pageSize) {
        return userService.getUserList(currentPage,pageSize,num);
    }

    @DeleteMapping("/delete")
    public Result<?> deleteUser(@RequestParam("id") Integer id) {
        log.info("delete user id:{}", id);
        return userService.deleteUser(id);
    }
}
