package com.datn.ecm.model.product;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Category implements Serializable {

    private long id;

    private String name;

    private String description;

    private String image;

}
