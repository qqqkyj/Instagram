package com.example.instagram.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "posts")
@NoArgsConstructor
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "image_url")
    private String imageUrl;

    @Builder
    public Post(String content, User user, String imageUrl) {
        this.content = content;
        this.user = user;
        this.imageUrl = imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // 게시글 삭제 시 댓글도 삭제
    @OneToMany(
            mappedBy = "post",       // 관계의 주인 설정
            cascade = CascadeType.ALL, // 삭제뿐 아니라 추가/수정도 cascade
            orphanRemoval = true      // 리스트에서 제거 시 DB에서도 삭제
    )
    private List<Comment> comments = new ArrayList<>();

    // Comment 추가/삭제 helper
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setPost(null);
    }
}
