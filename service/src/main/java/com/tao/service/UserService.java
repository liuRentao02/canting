package com.tao.service;

import com.tao.dto.RegisterUser;
import com.tao.pojo.User;
import com.tao.utils.Result;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author tao
 * @since 2025-09-17
 */
public interface UserService{

    User getUserByUsername(String username);

    Result<?> registerUser(RegisterUser registerUser);
}
