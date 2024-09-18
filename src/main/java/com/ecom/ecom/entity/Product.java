package com.ecom.ecom.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 500, unique = true)
    private String name;

    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Column(name = "price", nullable = false)
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Integer price;

    @Column(name = "stock", nullable = false)
    private String stock;

    @Column(name = "img_url", nullable = false)
    private String img_url;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", nullable = false)
    private Date created_at;

    @Temporal(TemporalType.DATE)
    @Column(name = "updated_at")
    private Date updated_at;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "category_id", nullable = false) // Foreign key to Category
//    private Category category;
}