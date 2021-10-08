package com.sk.user.config.auth;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
/*
 * @Configuration
 * 
 * @EnableWebSecurity
 * 
 * @RequiredArgsConstructor
 */
public class Custom2Securityconfig extends WebSecurityConfigurerAdapter{
	
	
	
    @PostConstruct
    public void init() {
        log.info("CustomSecurityConfig init()");
    }

    /**
     * 비밀번호 암호화를 위한 Bean
     */
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()   //csrf 비활성화하고자 하는 경우
                .authorizeRequests()
                .antMatchers("/hello/**").access("hasRole('USER')")
                .antMatchers("/users/**").access("hasRole('ADMIN')")
                .antMatchers("/", "/**").permitAll()
                ;
    }

	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { auth .userDetailsService(new
	 * Custom2AuthenticationProvider()).passwordEncoder(passwordEncoder()); }
	 */
    

    

}
