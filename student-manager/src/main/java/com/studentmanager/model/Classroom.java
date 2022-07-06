package com.studentmanager.model;

import java.time.Instant;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
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
public class Classroom {
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column(columnDefinition = "NVARCHAR(50)")
    private String name;

    @Column(unique = true)
    private String inviteCode;

    @Column
    private String coverURL;

    @Column
    @Builder.Default
    private Instant createdAt = Instant.now();

    @PrePersist
    public void prePersist() {
        if (this.inviteCode == null) {
            this.inviteCode = UUID.randomUUID().toString().replace("-", "");
        }
    }
}
