package com.nosov.blog.controller;

import com.nosov.blog.domain.Message;
import com.nosov.blog.domain.User;
import com.nosov.blog.repository.MessageRepo;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {

  @Autowired
  private MessageRepo messageRepo;

  @Value("${upload.path}")
  private String uploadPath;

  @GetMapping("/")
  public String Greeting(Map<String, Object> model) {
    return "greeting";
  }

  @GetMapping("/main")
  public String main(Map<String, Object> model) {
    Iterable<Message> messages = messageRepo.findAll();

    model.put("messages", messages);

    return "main";
  }

  @PostMapping("/main")
  public String add(
      @AuthenticationPrincipal User user,
      @Valid Message message,
      BindingResult bindingResult,
      Model model,
      @RequestParam("file") MultipartFile file
  ) throws IOException {

    if (bindingResult.hasErrors()) {
      Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
      model.addAttribute("message", message);
      model.mergeAttributes(errors);
    } else {
      message.setAuthor(user);
      saveFile(message, file);
      messageRepo.save(message);
      model.addAttribute("message", null);
    }

    Iterable<Message> messages = messageRepo.findAll();
    model.addAttribute("messages", messages);

    return "main";
  }

  private void saveFile(Message message, MultipartFile file) throws IOException {
    if (Objects.nonNull(file) && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
      File uploadDir = new File(uploadPath);
      if (!uploadDir.exists()) {
        uploadDir.mkdir();
      }
      String uuidFileName = UUID.randomUUID().toString();
      String resultFileName = uploadPath + "/" + uuidFileName + "." + file.getOriginalFilename();
      file.transferTo(new File(resultFileName));
      message.setFilename(resultFileName);
    }
  }


  @PostMapping("filter")
  public String filter(@RequestParam String filter, Map<String, Object> model) {
    Iterable<Message> messages;

    if (filter != null && !filter.isEmpty()) {
      messages = messageRepo.findByTag(filter);
    } else {
      messages = messageRepo.findAll();
    }

    model.put("messages", messages);

    return "main";
  }


  @GetMapping("/user-messages/{user}")
  public String getUserMessages(
      @AuthenticationPrincipal User currentUser,
      @PathVariable User user,
      Model model,
      @RequestParam(required = false) Long messageId) {

    Set<Message> messages = user.getMessages();
    Message message = messageId != null ? messages.stream().filter(x -> x.getId().equals(messageId)).findFirst().get() : null;
    model.addAttribute("messages", messages);
    model.addAttribute("message", message);
    model.addAttribute("isCurrentUser", user.equals(currentUser));
    return "userMessages";
  }

  @PostMapping("/user-messages/{user}")
  public String updateMessage(
      @AuthenticationPrincipal User currentUser,
      @PathVariable User user,
      @RequestParam Long messageId,
      @RequestParam String text,
      @RequestParam String tag,
      @RequestParam MultipartFile file
  ) throws IOException {

    Message message = messageRepo.findById(messageId).stream().findFirst().get();

    if (currentUser.equals(message.getAuthor())) {
      if (!StringUtils.isEmpty(text)) {
        message.setText(text);
      }
      if (!StringUtils.isEmpty(tag)) {
        message.setTag(tag);
      }
      saveFile(message, file);

      messageRepo.save(message);
    } else {
      // TODO: 05.02.2022 Сообщение об ошибке.
    }

    return "redirect:/user-messages/" + user.getId();
  }

}