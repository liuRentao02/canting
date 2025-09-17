package com.tao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Login
 *
 * @author LiuRentao
 * @version 1.0
 * @since 2025/9/16 23:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login {
    private String username;
    private String password;
}
