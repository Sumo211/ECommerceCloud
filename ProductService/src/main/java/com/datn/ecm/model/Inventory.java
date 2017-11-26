package com.datn.ecm.model;

import com.datn.ecm.constant.InventoryStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long clothesId;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private InventoryStatus status;

    @Builder
    public Inventory(long clothesId, int quantity, InventoryStatus status) {
        this.clothesId = clothesId;
        this.quantity = quantity;
        this.status = status;
    }

}
