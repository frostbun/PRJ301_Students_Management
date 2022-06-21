package com.studentmanager.model;

import java.time.Instant;

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
public class ClassMember {
    public static final String TEACHER = "TEACHER";
    public static final String STUDENT = "STUDENT";

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn
    private Classroom classroom;

    @ManyToOne
    @JoinColumn
    private Account account;

    @Column
    private String role;

    @Column
    @Builder.Default
    private Instant createdAt = Instant.now();
}
