package com.nosov.blog.controller;

import com.nosov.blog.domain.User;
import com.nosov.blog.domain.dto.CaptcaResponseDto;
import com.nosov.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistarationController {
    private static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Autowired
    UserService userService;

    @Value("${recaptcha.secret}")
    String recapchaSecret;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/registration")
    private String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    private String addUser(
            @RequestParam("g-recaptcha-response") String reCapthcaResponse,
            @RequestParam("password2") String passwordConfirm,
            @Valid User user,
            BindingResult bindingResult,
            Model model) {


        String url = String.format(CAPTCHA_URL, recapchaSecret, reCapthcaResponse);
        CaptcaResponseDto captcaResponseDto = restTemplate.postForObject(url, Collections.EMPTY_LIST, CaptcaResponseDto.class);
        if (!captcaResponseDto.isSuccess()) {
            model.addAttribute("captchaError", "Fill captcha");
        }

        boolean isPasswordConfirmEmpty = StringUtils.isEmpty(passwordConfirm);
        if (isPasswordConfirmEmpty) {
            model.addAttribute("password2Error", "Password confirmation can't be empty");
        }
        if (user.getPassword() != null && !user.getPassword().equals(passwordConfirm)) {
            model.addAttribute("passwordError", "Password are different!");
        }
        if (bindingResult.hasErrors() || !captcaResponseDto.isSuccess()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
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
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code is not found!");
        }

        return "login";
    }
}