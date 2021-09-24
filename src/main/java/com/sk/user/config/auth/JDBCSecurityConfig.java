package com.sk.user.config.auth;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class JDBCSecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final DataSource dataSource;
	
	@PostConstruct
	public void init() {
		log.info("JDBCSecurityConfig init()");
	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }
	
	@Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/users/**").hasRole("ADMIN").antMatchers("/").permitAll()
			.and()
				.formLogin()
				.loginPage("/auth/login") // 로그인 처리 POST 는 동일한 경로로
				.defaultSuccessUrl("/")
			.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/").invalidateHttpSession(true)
			.and().csrf().ignoringAntMatchers("h2-console/**")
			.and().headers()
				.addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		/**
		 * 기본 query
		 * select username, password, enabled from user where username=?
		 * select username, authority from authorities where username=?
		 * 
		 */
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			//.rolePrefix("ROLE_") //prefix를 설정하면 DB에는 ROLE_을 제외하여 저장한다. 해당 설정이 없으면 ROLE_을 붙여준다.
			.usersByUsernameQuery("select user_id as username, password, enabled from t_user where user_id = ?")
			.authoritiesByUsernameQuery("select user_id as username, AUTH as authority from t_user where user_id = ?")
			.passwordEncoder(passwordEncoder());
	}

}
