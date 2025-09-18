package com.tao.conversion;

import com.tao.dto.RegisterUser;
import com.tao.pojo.User;
import com.tao.vo.UserListVo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * RegisterUserToUserPojo
 *
 * @author LiuRentao
 * @version 1.0
 * @since 2025/9/17 20:01
 */
@Component
public class ObjectToNew {

    private final   PasswordEncoder passwordEncoder;

    public ObjectToNew(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public  User getUser(RegisterUser registerUser) {
        User user = new User();
        user.setUsername(registerUser.getUsername());
        user.setPassword(passwordEncoder.encode(registerUser.getPassword())); // 注意：这里需要加密
        user.setAvatar(registerUser.getAvatar());
        user.setPhone(registerUser.getPhone());
        user.setRoleId(registerUser.getRoleId() != null ? registerUser.getRoleId() : 1);
        user.setNickname(registerUser.getNickname());
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setStatus(1); // 默认启用状态
        return user;
    }

    public UserListVo getUserList(User user,String role) {
        UserListVo userListVo = new UserListVo();

        userListVo.setRole(role);

        userListVo.setId(user.getId());
        userListVo.setUsername(user.getUsername());
        userListVo.setPassword(user.getPassword());
        userListVo.setAvatar(user.getAvatar());
        userListVo.setCreateTime(user.getCreateTime());
        userListVo.setUpdateTime(user.getUpdateTime());
        userListVo.setNickname(user.getNickname());
        userListVo.setPhone(user.getPhone());
        userListVo.setStatus(user.getStatus());
        return userListVo;
    }
}
