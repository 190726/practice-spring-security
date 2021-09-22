package com.sk.hello.web;

import com.sk.hello.domain.Hello;
import com.sk.hello.domain.HelloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("hello")
public class HelloController  implements WebMvcConfigurer {

    private final HelloRepository repository;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
    }

    @GetMapping("/save")
    public String hello() {
        // test 로직
        repository.save(new Hello(1L, "ss"));
        repository.save(new Hello(2L, "ss1"));
        repository.save(new Hello(3L, "ss2"));
        repository.save(new Hello(4L, "ss3"));
        return "hello save called.";
    }

    @GetMapping("/hellos")
    public List<Hello> hello2() {
        return repository.findAll();

    }
}
