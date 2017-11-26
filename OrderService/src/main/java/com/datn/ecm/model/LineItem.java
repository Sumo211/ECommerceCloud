package com.datn.ecm.model;

import lombok.Data;

@Data
public class LineItem {

    private long clothesId;

    private Clothes clothes;

    private int quantity;

    public LineItem() {

    }

}