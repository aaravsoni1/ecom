package com.ecom.ecom.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "review_images")
public class ReviewImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ElementCollection // This annotation specifies that we are storing a list of values
    @CollectionTable(name = "review_image_urls", joinColumns = @JoinColumn(name = "review_id"))
    @Column(name = "image_url", nullable = false)
    private List<String> imageUrls; // Storing list of image URLs

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

}