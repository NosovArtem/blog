package com.nosov.blog.repository;

import com.nosov.blog.domain.Message;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepo extends CrudRepository<Message, Integer> {

  List<Message> findByTag(String tag);
  List<Message> findById(long id);
}
