package com.company.usermanagement.controller;

import com.company.usermanagement.service.UserService;
import com.company.usermanagement.session.UserLoginSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final UserService userService;
    private final UserLoginSession userLoginSession;

    @GetMapping("/dashboard")
    public String dashboard(Model model){
        System.out.println("------------ enter into Dashboard -----------");
        model.addAttribute("userSession",userLoginSession);
        model.addAttribute("users",userService.getAllUsers());
        return "dashboard";
    }
}
