package com.tao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UpdateUser
 *
 * @author LiuRentao
 * @version 1.0
 * @since 2025/9/18 16:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUser {
    private Integer id;
    private String nickname;
    private String password;
    private String phone;
}
