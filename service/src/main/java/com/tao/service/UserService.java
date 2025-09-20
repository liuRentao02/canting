package com.tao.service;

import com.tao.dto.RegisterUser;
import com.tao.dto.UpdateUser;
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

    Result<?> updateUser(UpdateUser user);

    Result<?> getUserList(Integer currentPage,Integer pageSize,Integer num);

    Result<?> deleteUser(Integer id);
}
