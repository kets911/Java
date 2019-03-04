package com.pavel_katsuba.talks.controllers;

import com.pavel_katsuba.talks.beans.User;
import com.pavel_katsuba.talks.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class MainController {
    private final MessageRepo messageRepo;

    @Value("${spring.profiles.active}")
    private String profile;

    public MainController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }


    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user){
        Map<String, Object> data = new HashMap<>();
        if (user != null) {
            data.put("profile", user);
            data.put("messages", messageRepo.findAll());
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));
        return "index";
    }
}
