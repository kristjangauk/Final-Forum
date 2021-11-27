package com.example.finalforum.repositories;

import com.example.finalforum.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Long countTopicsByUser_Id(Long id);

    Topic findTopicById(Long id);

    List<Topic> findTopicsByCategoryOrderByCreatedDateDesc(String category);
    List<Topic> findTopicsByUser_IdOrderByCreatedDateDesc(Long id);
}
