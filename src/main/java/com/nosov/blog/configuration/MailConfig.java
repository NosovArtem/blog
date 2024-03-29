package com.nosov.blog.configuration;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

  @Value("${spring.mail.host}")
  private String host;
  @Value("${spring.mail.port}")
  private int port;
  @Value("${spring.mail.username}")
  private String username;
  @Value("${spring.mail.password}")
  private String password;
  @Value("${spring.mail.protocol}")
  private String protocol;
  @Value("${mail.debug}")
  private String debug;

  @Bean
  public JavaMailSender getMailSender() {
    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
    javaMailSender.setHost(host);
    javaMailSender.setPort(port);
    javaMailSender.setUsername(username);
    javaMailSender.setPassword(password);

    Properties properties = javaMailSender.getJavaMailProperties();
    properties.setProperty("mail.transport.protocol", protocol);
    properties.setProperty("mail.debug", debug);

    return javaMailSender;
  }
}
