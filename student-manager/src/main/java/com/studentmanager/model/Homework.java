package com.studentmanager.model;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class Homework {
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn
    private Classroom classroom;

    @ManyToOne
    @JoinColumn
    private Account author;

    @Column(columnDefinition = "VARCHAR(MAX)")
    private String filePath;

    @Column(columnDefinition = "NVARCHAR(50)")
    private String title;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column
    private Double maxMark;

    @Column
    private LocalDateTime deadline;

    @Column
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "homework", cascade = CascadeType.ALL)
    private List<Submission> submissions;

    public String getFileName() {
        return new File(filePath).getName();
    }

    public String getDeadline() {
        if (deadline == null) {
            return null;
        }
        return DateTimeConfig.FMT.format(deadline);
    }

    public LocalDateTime getRawDeadline() {
        return deadline;
    }

    public String getCreatedAt() {
        if (createdAt == null) {
            return null;
        }
        return DateTimeConfig.FMT.format(createdAt);
    }
}
