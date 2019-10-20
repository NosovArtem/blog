package com.nosov.blog.controller;

import com.nosov.blog.domain.Role;
import com.nosov.blog.domain.User;
import com.nosov.blog.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistarationController {

    @Autowired
    UserRepo userRepo;

    @GetMapping("/registration")
    private String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    private String addUser(User user, Map<String, Object> model) {
        User byUsername = userRepo.findByUsername(user.getUsername());
        if (byUsername != null) {
            model.put("message", "User alredi exist");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);

        return "redirect:/login";
    }
}