package com.springapp.springapp.controllers;

import com.springapp.springapp.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "welcome page");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About us page");
        return "about";
    }



}
