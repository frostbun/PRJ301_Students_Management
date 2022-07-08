package com.studentmanager.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.studentmanager.config.ImageConfig;

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
public class Classroom {
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column(columnDefinition = "NVARCHAR(50)")
    private String name;

    @Column(unique = true)
    private String inviteCode;

    @Column(columnDefinition = "VARCHAR(MAX)")
    private String coverURL;

    @Column
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    public void prePersist() {
        if (this.inviteCode == null) {
            this.inviteCode = UUID.randomUUID().toString().replace("-", "");
        }
    }

    public String getShortName() {
        return name.length() > 10 ? name.substring(0, 10) + "..." : name;
    }

    public String getCoverURL() {
        return coverURL == null ? ImageConfig.DEFAULT_CLASS_COVER : coverURL;
    }
}
