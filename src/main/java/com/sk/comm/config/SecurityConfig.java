package com.sk.comm.config;

import com.sk.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    /**
     * 비밀번호 암호화를 위한 Bean
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.csrf().disable()
                /* http request 권한 설정 */
                .authorizeRequests()
                //.antMatchers("/hello/**", "/users/**").authenticated()
                //.antMatchers("/hello/**").hasRole("USER")
                .antMatchers("/hello/**").access("hasRole('USER')")
                .antMatchers("/users/**").access("hasRole('ADMIN')")
                .antMatchers("/", "/**").permitAll()
        .and()
            .formLogin()
                .loginPage("/my/login") // 로그인 처리 POST 는 동일한 경로로
                .defaultSuccessUrl("/")
        .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/my/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
        .and()
                /* for H2 console */
                .csrf().ignoringAntMatchers("/h2-console/**")
        .and()
                .headers().addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
        .and()
            .exceptionHandling()
                .accessDeniedPage("/denied");
            ;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        /*auth.inMemoryAuthentication()
                .withUser("user").password("{noop}1111").authorities("ROLE_USER")
                .and()
                .withUser("admin").password("{noop}1111").authorities("ROLE_ADMIN","ROLE_USER");*/
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());

    }
}
