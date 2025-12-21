package com.example.onlineeducationplatform.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "discussions")
public class Discussion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    // For controller compatibility
    public Integer getCourseId() {
        return course != null ? course.getId() : null;
    }
    public void setCourseId(Integer courseId) {
        if (this.course == null) this.course = new Course();
        this.course.setId(courseId);
    }

    @Column(name = "user_id")
    private Integer userId;

    private String title;
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    // Replies can be mapped as OneToMany if DiscussionReply is an entity
    // @OneToMany(mappedBy = "discussion", cascade = CascadeType.ALL, orphanRemoval
    // = true)
    // private List<DiscussionReply> replies;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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
    // public List<DiscussionReply> getReplies() { return replies; }
    // public void setReplies(List<DiscussionReply> replies) { this.replies =
    // replies; }
}
