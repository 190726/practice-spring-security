package com.sk.user.web;

import com.sk.user.domain.User;
import com.sk.user.domain.UserRepository;
import com.sk.user.dto.UserLoginRequestDto;
import com.sk.user.dto.UserResponseDto;
import com.sk.user.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/users")
    public String users(Model model){
        final List<User> users = userRepository.findAll();
        List<UserResponseDto> userResponses = users.stream().map(UserResponseDto::new).collect(Collectors.toList());
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
        userRepository.save(userSaveRequestDto.toEntity());
        return "redirect:/users";
    }

    @GetMapping("/denied")
    public String denied(){
        return "users/denied";
    }

    @GetMapping("/my/login")
    public String login(){
        return "users/login";
    }

    /*@PostMapping("/login")
    public String login(UserLoginRequestDto dto) {
        log.debug("UserLoginRequestDto param is {}", dto);
        return "users/login";
    }*/
}
