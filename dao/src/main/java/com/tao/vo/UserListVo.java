package com.tao.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * UserListVo
 *
 * @author LiuRentao
 * @version 1.0
 * @since 2025/9/18 18:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListVo {
    private Integer id;
    private String username;
    private String password;
    private String phone;
    private String avatar;
    private String role;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String nickname;
}
