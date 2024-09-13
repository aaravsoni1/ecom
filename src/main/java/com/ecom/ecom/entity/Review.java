package com.ecom.ecom.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "rating", nullable = false)
    private Float rating;

    @Column(name = "comment", nullable = false, length = 500)
    private String comment;

    @Column(name = "image_url")
    private String image_url;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
    private Date created_at;

    @Temporal(TemporalType.DATE)
    @Column(name = "updated_at")
    private Date updated_at;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}