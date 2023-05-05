package com.brainstation23.erp.controller.web;

import com.brainstation23.erp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping
    public String home(Model model) {
        model.addAttribute("pageTitle", "Home");
        return "home";
    }

    @GetMapping("/dashboard")
    public String afterLoginAdminPanel(Principal principal, Model model) {
        model.addAttribute("loggedInUser", userService.getLoggedInUser(principal));
        model.addAttribute("pageTitle", "Dashboard");
        return "index";
    }

    @GetMapping("/login")
    @Secured("!isAuthenticated()")
    public String viewLoginPage(Model model) {
        model.addAttribute("pageTitle", "Login");
        return "login";
    }

}