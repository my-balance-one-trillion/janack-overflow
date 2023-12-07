package com.example.janackoverflow.community.entity;

import com.example.janackoverflow.issue.entity.issue;
import com.example.janackoverflow.global.entity.AuditingFields;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "likes")
public class Likes extends AuditingFields {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="issue_id")
    private issue issue;

}