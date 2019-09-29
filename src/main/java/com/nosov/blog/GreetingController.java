package com.nosov.blog;

import com.nosov.blog.domain.Message;
import com.nosov.blog.repository.MessageRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingController {

    @Autowired
    MessageRepos messageRepos;

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name = "name", required = false, defaultValue = "World") String name,
            Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(Map<String, Object> model) {
        model.put("title", "Hello, lets start dev blog");

        Iterable<Message> mess = messageRepos.findAll();
        model.put("messages", mess);
        return "main";
    }

    @PostMapping("add")
    public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model){
        Message message = new Message(text, tag);
        messageRepos.save(message);
        Iterable<Message> mess = messageRepos.findAll();
        model.put("messages", mess);
        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Message> mess;
        if(filter != null && !filter.isEmpty()){
           mess = messageRepos.findByTag(filter);
        }else{
            mess = messageRepos.findAll();
        }

        model.put("messages", mess);
        return "main";
    }

}