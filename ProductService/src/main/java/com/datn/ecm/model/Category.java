package com.datn.ecm.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    private String image;

    @Builder
    public Category(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

}
