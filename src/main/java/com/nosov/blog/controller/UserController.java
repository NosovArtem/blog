package com.nosov.blog.controller;

import com.nosov.blog.domain.Role;
import com.nosov.blog.domain.User;
import com.nosov.blog.service.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

  @Autowired
  UserService userService;

  @GetMapping
  @PreAuthorize("hasAuthority('ADMIN')")//(Доступ только для ADMIN)
  public String userList(Model model) {
    model.addAttribute("users", userService.findAll());
    return "userList";
  }

  @GetMapping("{user}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public String userEditForm(@PathVariable User user, Model model) {
    model.addAttribute("user", user);
    model.addAttribute("roles", Role.values());
    return "userEdit";
  }

  @PostMapping
  @PreAuthorize("hasAuthority('ADMIN')")
  public String userSave(@RequestParam String username, @RequestParam Map<String, String> form,
      @RequestParam("userId") User user) {
    userService.saveUser(user, username, form);
    return "redirect:/user";
  }

  @GetMapping("profile")
  public String getProfile(Model model, @AuthenticationPrincipal User user) {
    model.addAttribute("username", user.getUsername());
    model.addAttribute("email", user.getEmail());

    return "profile";
  }

  @PostMapping("profile")
  public String updateProfile(
      @AuthenticationPrincipal User user,
      @RequestParam String email,
      @RequestParam String password
  ) {
    userService.updateProfile(user, email, password);

    return "redirect:/user/profile";
  }
}
