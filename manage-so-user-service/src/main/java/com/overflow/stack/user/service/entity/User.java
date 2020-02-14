package com.overflow.stack.user.service.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "SO_USER")
public class User {

    @Id
    private String userId;

    @Column(unique = true)
    private String emailId;

    private String password;

    private String displayName;

}
