package com.example.itmoProject.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class HomeController {
    @GetMapping("")
    public String main() {
        return "Добро пожаловать в приложение";
    }

}
