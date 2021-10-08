package com.sk.user.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sk.user.dto.UserDetailDto;
import com.sk.user.dto.UserLoginRequestDto;
import com.sk.user.dto.UserResponseDto;
import com.sk.user.dto.UserSaveRequestDto;
import com.sk.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public String users(Model model){
        List<UserResponseDto> userResponses = userService.findAll();
        model.addAttribute("users", userResponses);
        return "users/users";
    }

    @GetMapping("/users/register")
    public String registerForm(UserSaveRequestDto userSaveRequestDto, Authentication authentication) {
    	//String loginId = (String)authentication.getPrincipal();
    	//log.debug("user register login id is {}", loginId);
        return "users/register";
    }

    @PostMapping("/users/register")
    public String register(@Valid UserSaveRequestDto userSaveRequestDto, BindingResult bindingResult
    		, Authentication authentication, UserDetailDto userDatilDto) {
    	
        if (bindingResult.hasErrors()) return "users/register";

        log.debug("User Register Post Request is {}", userSaveRequestDto);
        log.debug("User Register creator is {}", authentication.getDetails());
        log.debug("User Register creator is {}", userDatilDto);
        
       //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        userService.register(userSaveRequestDto);
        return "redirect:/users";
    }

    @GetMapping("/denied")
    public String denied(){
        return "users/denied";
    }

    @GetMapping("/auth/login")
    public String login(){
        return "users/login";
    }

    /**
     * Custom2Securityconfig로 설정 후, 
     * Controller 에서 로그인 처리 
     */
    @PostMapping("/auth/login")
    public String login(UserLoginRequestDto dto) {
        log.debug("UserLoginRequestDto param is {}", dto);
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        
        PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(userService, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(token);
        
        return "redirect:/";
    }
}
