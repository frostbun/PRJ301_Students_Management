package com.studentmanager.model;

import java.time.Instant;
import java.util.List;

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

    @Column
    private String filePath;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String title;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column
    private Double maxMark;

    @Column
    private Instant deadline;

    @Column
    @Builder.Default
    private Instant createdAt = Instant.now();

    @OneToMany(mappedBy = "homework")
    private List<Submission> submissions;

    public String getFileName() {
        return filePath.substring(filePath.lastIndexOf('/') + 1);
    }

    public String getDeadline() {
        if (deadline == null) {
            return null;
        }
        return DateTimeConfig.FMT.format(deadline);
    }

    public String getCreatedAt() {
        if (createdAt == null) {
            return null;
        }
        return DateTimeConfig.FMT.format(createdAt);
    }
}
