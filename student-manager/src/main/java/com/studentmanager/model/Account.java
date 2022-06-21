package com.studentmanager.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Account {
    @Id
    @Column
    private String username;

    @Column
    private String password;

    @Column(columnDefinition = "NVARCHAR(25)")
    private String firstName;

    @Column(columnDefinition = "NVARCHAR(25)")
    private String lastName;

    @Column
    private String phone;

    @Column
    private String email;

    @Column(columnDefinition = "NVARCHAR(50)")
    private String address;

    @Column
    private String avatarURL;

    @Column
    private String role;

    @Column
    @Builder.Default
    private Instant createdAt = Instant.now();
}
