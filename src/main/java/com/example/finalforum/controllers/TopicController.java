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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Controller
public class TopicController {

    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public TopicController(UserRepository userRepository, TopicRepository topicRepository, AnswerRepository answerRepository) {
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.answerRepository = answerRepository;
    }

    @GetMapping("topic/{id}")
    public String displayTopic(@PathVariable String id,  Model model) {
//        String username = User.getUsername();
//        Long idUser = userRepository.getUserByUsername(username).getId();

        Topic topic = topicRepository.findTopicById(Long.valueOf(id));
        List<Answer> answers = answerRepository.findAnswerByTopic_Id(Long.valueOf(id));

        model.addAttribute("topic", topic);
        model.addAttribute("answers", answers);
//        model.addAttribute("idUser", idUser);
        return "topic";
    }

    @PostMapping("topic/{id}")
    public View updateAnswer(@RequestParam String id_topic, @RequestParam String action, @RequestParam String id_answer,
                             @RequestParam(required = false) String state, HttpServletRequest request) {
        switch (action) {
            case "useful" :
                answerRepository.setUsefulForAnswer(!Boolean.parseBoolean(state), Long.valueOf(id_answer));
                break;
            case "delete" :
                answerRepository.deleteAnswerById(Long.valueOf(id_answer));
                break;
        }
        String contextPath = request.getContextPath();
        return new RedirectView(contextPath + "/topic/" + id_topic);
    }

    @PostMapping("topic")
    public View addAnswer(@RequestParam("content") String content, @RequestParam("code") String code,
                          @RequestParam("id_topic") String id_topic, @RequestParam("id_user") String id_user,
                          HttpServletRequest request) {
        Answer answer = new Answer();
        answer.setContent(content);

        // I know that it can be blank field, but I did it on purpose to find out about Optionals:
        if (Objects.equals(code, ""))
            answer.setCode(null);
        else
            answer.setCode(code);
        answer.setCreatedDate(LocalDateTime.now());
        answer.setUseful(false);
        answer.setTopic(topicRepository.findTopicById(Long.valueOf(id_topic)));
        answer.setUser(userRepository.getUserById(Long.parseLong(id_user)));

        answerRepository.save(answer);
        String contextPath = request.getContextPath();
        return new RedirectView(contextPath + "/topic/" + id_topic);
    }
}