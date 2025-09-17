package com.tao.web.controller;

import com.tao.dto.Login;
import com.tao.pojo.User;
import com.tao.service.UserService;
import com.tao.utils.JwtUtils;
import com.tao.utils.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<?> login(@RequestBody Login loginRequest) {
        try {
            log.info("收到登录请求: {}", loginRequest.getUsername());

            // 进行认证
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // 认证成功，生成JWT
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            User user = userService.getUserByUsername(loginRequest.getUsername());
            // 返回token和信息
            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("type", "Bearer");
            response.put("username", authentication.getName());
            if (user!=null){
                response.put("nickname", user.getNickname());
            }
            log.info("登录成功，返回token: {}", jwt);
            return Result.success(response);

        } catch (Exception e) {
            log.error("登录失败: {}", e.getMessage());
            return Result.error("用户名或密码错误: " + e.getMessage());
        }
    }

    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<?> logout() {
        SecurityContextHolder.clearContext();
        return Result.success("退出成功");
    }
}