package com.datn.ecm.model;

import com.datn.ecm.constant.Color;
import com.datn.ecm.constant.Material;
import com.datn.ecm.constant.Size;
import lombok.Data;

@Data
public class Clothes {

    private long id;

    private long categoryId;

    private String name;

    private String sku;

    private String description;

    private String image;

    private double price;

    private String manufacture;

    private Size size;

    private Color color;

    private Material material;

    private double discount;

    private long createdAt;

    private long lastModified;

    public Clothes() {

    }

}
