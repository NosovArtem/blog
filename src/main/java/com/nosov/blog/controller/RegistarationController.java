package com.nosov.blog.controller;

import com.nosov.blog.domain.User;
import com.nosov.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistarationController {

    @Autowired
    UserService userService;

    @GetMapping("/registration")
    private String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    private String addUser(@Valid User user, BindingResult bindingResult, Model model) {
        if (user.getPassword() != null && !user.getPassword().equals(user.getPassword2())) {
            model.addAttribute("passwordError", "Password are different!");
        }
        if (bindingResult.hasErrors()) {
            ControllerUtils.setErrorsToModel(bindingResult, model);
            return "registration";
        }
        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User alredi exist");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    private String activateUser(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("message", "Activation code is not found!");
        }

        return "login";
    }
}