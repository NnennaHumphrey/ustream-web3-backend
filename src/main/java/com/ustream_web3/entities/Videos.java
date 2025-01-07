package com.ustream_web3.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="videos")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Videos {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)// This ensures the UUID is stored as a CHAR(36)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "Video_url",nullable = false)
    private String videoUrl;

    @Column(name = "video_name", nullable = false)
    private String videoName;

    @CreatedDate
    @Column(name = "upload_date", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name ="description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comments> comments;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
