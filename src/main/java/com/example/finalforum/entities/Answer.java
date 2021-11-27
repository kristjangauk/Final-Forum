package com.example.finalforum.entities;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1024)
    private String content;

    @Column(nullable = false)
    private boolean useful;

    private LocalDateTime createdDate;

    @Column(length = 1024)
    private String code;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_topic")
    private Topic topic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isUseful() {
        return useful;
    }

    public void setUseful(boolean useful) {
        this.useful = useful;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Optional getCode() {
        return Optional.ofNullable(code);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String displayParsedCreatedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - dd.MM.yyyy");
        return this.createdDate.format(formatter);
    }

    public String displayCode() {
        if (Optional.ofNullable(code).isPresent())
            return Optional.ofNullable(code).get();
        else
            return "";
    }

    public String displayBeginning() {
        return (this.content.length() < 32) ? this.content.concat("...") : this.content.substring(0, 30).concat("...");
    }
}
