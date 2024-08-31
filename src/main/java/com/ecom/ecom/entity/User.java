package com.ecom.ecom.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Column(name = "firstname", nullable = false)
    private String firstname;
    private String lastname;
    private String mobile;
    private String email;

}