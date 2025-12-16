package com.example.onlineeducationplatform.model;

import java.util.Date;
import java.util.List;

public class Discussion {
    private Integer id;
    private Integer courseId;
    private Integer userId;
    private String title;
    private String content;
    private Date createdAt;
    private List<DiscussionReply> replies;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<DiscussionReply> getReplies() {
        return replies;
    }

    public void setReplies(List<DiscussionReply> replies) {
        this.replies = replies;
    }
}
