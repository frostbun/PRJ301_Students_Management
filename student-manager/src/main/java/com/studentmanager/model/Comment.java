package com.studentmanager.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment { //Comment
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn
    private Account author; // Account author

    @ManyToOne
    @JoinColumn
    private Homework homework; // remove

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String content;

    @Column
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
