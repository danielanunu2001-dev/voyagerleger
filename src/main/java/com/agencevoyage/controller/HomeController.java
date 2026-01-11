package com.agencevoyage.controller;

import com.agencevoyage.service.TravelPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final TravelPackageService travelPackageService;

    @Autowired
    public HomeController(TravelPackageService travelPackageService) {
        this.travelPackageService = travelPackageService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("packages", travelPackageService.findAll());
        return "index";
    }

    @GetMapping("/profil")
    public String profil() {
        return "profil";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
