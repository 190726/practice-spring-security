package com.sk.basic.web;

import com.sk.basic.domain.Hello;
import com.sk.basic.domain.HelloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("hello")
public class HomeController {

    private final HelloRepository repository;

    @GetMapping("/save")
    public String hello() {
        repository.save(new Hello(1L, "ss"));
        repository.save(new Hello(2L, "ss1"));
        return "hello save called.";
    }

    @GetMapping("/hellos")
    public List<Hello> hello2() {
        return repository.findAll();

    }
}
