package com.datn.ecm.model;

import com.datn.ecm.constant.Color;
import com.datn.ecm.constant.Material;
import com.datn.ecm.constant.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "clothes")
@Data
@NoArgsConstructor
public class Clothes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private long categoryId;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(unique = true)
    private String sku;

    private String image;

    @Column(nullable = false)
    private double price;

    private String manufacture;

    @Enumerated(EnumType.STRING)
    private Size size;

    @Enumerated(EnumType.STRING)
    private Color color;

    @Enumerated(EnumType.STRING)
    private Material material;

    private double discount;

    @CreatedDate
    private long createdAt;

    @LastModifiedDate
    private long lastModified;

    @Builder
    public Clothes(long categoryId, String name, String sku, String description, String image, double price, String manufacture,
                   Size size, Color color, Material material, double discount) {
        this.categoryId = categoryId;
        this.name = name;
        this.sku = sku;
        this.description = description;
        this.image = image;
        this.price = price;
        this.manufacture = manufacture;
        this.size = size;
        this.color = color;
        this.material = material;
        this.discount = discount;
    }

}
