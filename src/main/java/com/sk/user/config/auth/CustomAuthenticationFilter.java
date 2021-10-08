package com.sk.user.config.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public CustomAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
    	/*custom login success handler setting*/
    	setAuthenticationSuccessHandler(new CustomLoginSuccessHandler());
    	
        String email = request.getParameter("username");
        String credentials = request.getParameter("password");
        log.debug("\r\n ##[1]##CustomAuthenticationFilter's attemptAuthentication() called. user id is :{},password is {}", email, credentials);

        return getAuthenticationManager().authenticate(new CustomAuthenticationToken(email, credentials, null));
    }
    
    
}
