package com.sk.user.web;

import com.sk.user.dto.UserResponseDto;
import com.sk.user.dto.UserSaveRequestDto;
import com.sk.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

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
    public String registerForm(UserSaveRequestDto userSaveRequestDto) {
        return "users/register";
    }

    @PostMapping("/users/register")
    public String register(@Valid UserSaveRequestDto userSaveRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "users/register";

        log.debug("User Register Post Request is {}", userSaveRequestDto);
        userService.register(userSaveRequestDto);
        return "redirect:/users";
    }

    @GetMapping("/denied")
    public String denied(){
        return "users/denied";
    }

    @GetMapping("/login")
    public String login(){
        return "users/login";
    }

    /*@PostMapping("/login")
    public String login(UserLoginRequestDto dto) {
        log.debug("UserLoginRequestDto param is {}", dto);
        return "users/login";
    }*/
}
