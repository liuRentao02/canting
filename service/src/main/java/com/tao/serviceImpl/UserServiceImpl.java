package com.tao.serviceImpl;

import com.tao.dto.User;
import com.tao.service.UserService;
import com.tao.utils.Result;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

/**
 * UserServiceImpl
 *
 * @author LiuRentao
 * @version 1.0
 * @since 2025/9/16 22:51
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Override
    public Result<String> login() {
        return Result.success("success");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return new User(authorities);
    }
}
