package com.sk.user.web;

import com.sk.user.domain.User;
import com.sk.user.domain.UserRepository;
import com.sk.user.dto.UserResponseDto;
import com.sk.user.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("")
    public String users(Model model){
        final List<User> users = userRepository.findAll();
        List<UserResponseDto> userResponses = users.stream().map(UserResponseDto::new).collect(Collectors.toList());
        model.addAttribute("users", userResponses);
        return "users/list";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "users/registerForm";
    }

    @PostMapping("/register")
    public String register(UserSaveRequestDto userSaveRequestDto){
        log.debug("User Register Post Request is {}", userSaveRequestDto);
        userRepository.save(userSaveRequestDto.toEntity());
        return "redirect:/users";
    }
}
