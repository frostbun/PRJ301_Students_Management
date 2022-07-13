package com.studentmanager.model;

import java.io.File;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.studentmanager.config.DateTimeConfig;

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
public class Submission {
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn
    private Homework homework;

    @ManyToOne
    @JoinColumn
    private Account author;

    @Column(columnDefinition = "VARCHAR(MAX)")
    private String filePath;

    @Column
    private Double mark;

    @Column
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    public String getFileName() {
        return new File(filePath).getName();
    }

    public String getCreatedAt() {
        if (createdAt == null) {
            return null;
        }
        return DateTimeConfig.FMT.format(createdAt);
    }

    public boolean isLate() {
        if (homework.getDeadline() == null) {
            return false;
        }
        return createdAt.isAfter(homework.getRawDeadline());
    }

    public boolean checkAuthor(Account account) {
        return author.equals(account);
    }
}
