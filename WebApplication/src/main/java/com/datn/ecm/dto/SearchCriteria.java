package com.datn.ecm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SearchCriteria {

    private String name;

    private String category;

    private String sku;

    private String manufacture;

    private double priceFrom;

    private double priceTo;

    private String size;

    private List<String> colors;

    private List<String> materials;

}
