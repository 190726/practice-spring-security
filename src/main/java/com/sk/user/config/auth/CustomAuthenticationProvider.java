package com.sk.user.config.auth;

import com.sk.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@ComponentScan
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        
        log.debug("\r\n ##[2]##CustomAuthenticationProvider's authenticate() called. user id is :{},password is {}", username, password);

        UserDetails user = userService.loadUserByUsername(username);
        if (user == null) {
            throw new BadCredentialsException("username is not found. username=" + username);
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("password is not matched");
        }
        
        AbstractAuthenticationToken customAuthentication = new CustomAuthenticationToken(username, password, user.getAuthorities());
        customAuthentication.setAuthenticated(true);// 로그인 이후에는 doFileter()를 타지 않도록 함
        customAuthentication.setDetails(user);

        return customAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
    	log.debug("\r\n ##[2-2]##CustomAuthenticationProvider's supports() called");
        return CustomAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
