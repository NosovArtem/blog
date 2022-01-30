package com.nosov.blog.service;

import com.nosov.blog.domain.Role;
import com.nosov.blog.domain.User;
import com.nosov.blog.repository.UserRepo;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private MailSender mailSender;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Value("${hostname}")
  private String hostname;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepo.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }
    return user;
  }

  public boolean addUser(User user) {
    User userFromDb = userRepo.findByUsername(user.getUsername());

    if (userFromDb != null) {
      return false;
    }

    user.setActive(false);
    user.setRoles(Collections.singleton(Role.USER));
    user.setActivationCode(UUID.randomUUID().toString());
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    userRepo.save(user);

    if (!StringUtils.isEmpty(user.getEmail())) {
      sendActivationCode(user);
    }

    return true;
  }

  private void sendActivationCode(User user) {
    String message = String.format(
        "Hello %s! \n "
            + "Wellcome to Blog. "
            + "Please, visit next link: http://%s/activate/%s",
        user.getUsername(),
        hostname,
        user.getActivationCode()
    );

    mailSender.send(user.getEmail(), "Activation code", message);
  }

  public boolean activateUser(String code) {
    User user = userRepo.findByActivationCode(code);
    if (user == null) {
      return false;
    }
    user.setActive(true);
    userRepo.save(user);
    return true;
  }

  public void saveUser(User user, String username, Map<String, String> form) {
    user.setUsername(username);

    Set<String> roles = Arrays.stream(Role.values())
        .map(Role::name)
        .collect(Collectors.toSet());

    user.getRoles().clear();

    for (String key : form.keySet()) {
      if (roles.contains(key)) {
        user.getRoles().add(Role.valueOf(key));
      }

    }
    userRepo.save(user);
  }

  public List<User> findAll() {
    return userRepo.findAll();
  }

  public void updateProfile(User user, String email, String password) {
    String userEmail = user.getEmail();

    boolean isEmailChanged = (userEmail != null && !userEmail.equals(email))
        || (email != null && !email.equals(userEmail));
    if (isEmailChanged) {
      user.setEmail(email);
    }

    if (!StringUtils.isEmpty(password)) {
      user.setPassword(password);
    }

    userRepo.save(user);

    if (isEmailChanged) {
      sendActivationCode(user);
    }
  }
}
