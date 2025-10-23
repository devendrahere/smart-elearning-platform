package com.edusmart.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "courses")
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "course_title",nullable = false)
    private String courseTitle;

    @Column(name = "description",columnDefinition = "TEXT")
    private String courseDescription;

    @ManyToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name = "instructor_id",nullable = false)
    private Users instructor;

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST , CascadeType.MERGE})
    @JoinTable(
        name = "course_categories",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Categories> category=new HashSet<>();

    @OneToMany(mappedBy = "courses",cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<Lessons> lessons=new ArrayList<>();

    @OneToMany(mappedBy = "courses",cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<Enrollments> enrollments=new ArrayList<>();

    @OneToMany(mappedBy = "courses" ,cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<FileResource> materials=new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Discussions> discussions = new ArrayList<>();

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<DiscussionThread> discussionThreads=new ArrayList<>();

    @OneToMany(mappedBy = "course" ,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Announcements> announcements=new ArrayList<>();


    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt=LocalDateTime.now();
    }

    public Courses() {
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public Users getInstructor() {
        return instructor;
    }

    public void setInstructor(Users instructor) {
        this.instructor = instructor;
    }

    public List<Lessons> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lessons> lessons) {
        this.lessons = lessons;
    }

    public Set<Categories> getCategory() {
        return category;
    }

    public void setCategory(Set<Categories> category) {
        this.category = category;
    }

    public List<Enrollments> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollments> enrollments) {
        this.enrollments = enrollments;
    }

    public List<FileResource> getMaterials() {
        return materials;
    }

    public void setMaterials(List<FileResource> materials) {
        this.materials = materials;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Discussions> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(List<Discussions> discussions) {
        this.discussions = discussions;
    }

    public List<DiscussionThread> getDiscussionThreads() {
        return discussionThreads;
    }

    public void setDiscussionThreads(List<DiscussionThread> discussionThreads) {
        this.discussionThreads = discussionThreads;
    }

    public List<Announcements> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcements> announcements) {
        this.announcements = announcements;
    }
}
