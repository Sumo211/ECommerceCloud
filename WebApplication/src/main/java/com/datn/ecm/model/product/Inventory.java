package com.datn.ecm.model.product;

import com.datn.ecm.constant.InventoryStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Inventory implements Serializable {

    private long id;

    private long clothesId;

    private int quantity;

    private InventoryStatus status;

}
