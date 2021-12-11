package com.example.finalforum.controllers;


import com.example.finalforum.entities.Answer;
import com.example.finalforum.entities.Topic;
import com.example.finalforum.entities.User;
import com.example.finalforum.repositories.AnswerRepository;
import com.example.finalforum.repositories.TopicRepository;
import com.example.finalforum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Controller
public class TopicsController {

    private final TopicRepository topicRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;

    @Autowired
    public TopicsController(TopicRepository topicRepository, AnswerRepository answerRepository, UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("topics")
    public String displayAllTopics(Model model) {
        List<Topic> topics = topicRepository.findAll();
        String header = setHeader("all");
        model.addAttribute("topics", topics);
        model.addAttribute("header", header);
        model.addAttribute("answerRepository", answerRepository);
        return "/topics";
    }

    @PostMapping("user/save")
    public String addAnswer(@RequestParam("username") String username,
                            @RequestParam("password") String password
    ) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setCreatedDate(LocalDateTime.now());

        userRepository.save(user);
        return "redirect:/";
    }

    @PostMapping("topics/save")
    public String addAnswer(@RequestParam("title") String title,
                          @RequestParam("content") String content,
                          @RequestParam("category") String category,
                          @RequestParam("code") String code,
                          @RequestParam("id_user") String id_user,
                          HttpServletRequest request) {
        Topic topic = new Topic();
        topic.setTitle(title);

        if (Objects.equals(code, ""))
            topic.setCode(null);
        else
            topic.setCode(code);
        topic.setContent(content);
        topic.setCategory(category);
        topic.setCreatedDate(LocalDateTime.now());
        topic.setUser(userRepository.getUserById(Long.parseLong(id_user)));

        topicRepository.save(topic);
        return "redirect:/topics";
    }



    @GetMapping("topics/{category}")
    public String displayTopicsByCategory(@PathVariable String category, Model model) {
        List<Topic> topics = topicRepository.findTopicsByCategoryOrderByCreatedDateDesc(category);
        String header = setHeader(category);
        model.addAttribute("topics", topics);
        model.addAttribute("header", header);
        model.addAttribute("answerRepository", answerRepository);
        return "topics";
    }

    @GetMapping("topics/user/{id}")
    public String displayTopicsByUser(@PathVariable String id, Model model) {
        List<Topic> topics = topicRepository.findTopicsByUser_IdOrderByCreatedDateDesc(Long.valueOf(id));
        String header = setHeader("user");
        model.addAttribute("topics", topics);
        model.addAttribute("header", header);
        model.addAttribute("answerRepository", answerRepository);
        return "topics";
    }

    private String setHeader(String category) {
        switch (category) {
            case "se":
                return "Java Standard Edition";
            case "ee":
                return "Java Enterprise Edition";
            case "jpa":
                return "Java Persistence API and Hibernate";
            case "spring":
                return "Spring Framework";
            case "web":
                return "HTML/CSS/JavaScript";
            case "all":
                return "All topics";
            default:
                return "User's topics";
        }
    }
}