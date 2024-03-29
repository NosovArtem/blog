package com.nosov.blog.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Entity
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotBlank(message = "Please fill the meassage")
  @Length(max = 2048, message = "Message too long (more than 2048)")
  private String text;
  @Length(max = 255, message = "Tag too long (more than 255)")
  private String tag;
  private String filename;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User author;

  public Message() {
  }

  public Message(String text, String tag, User user) {
    this.author = user;
    this.text = text;
    this.tag = tag;
  }

  public String getAuthorName() {
    return author != null ? author.getUsername() : "<none>";
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }
}