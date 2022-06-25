package com.studentmanager.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

    @GeneratedValue
    @Column(unique = true)
    private String inviteCode;

    @Column
    private String coverURL;

    @Column
    @Builder.Default
    private Instant createdAt = Instant.now();
}
