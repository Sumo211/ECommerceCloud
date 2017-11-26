package com.datn.ecm.service.inventory;

import com.datn.ecm.model.Inventory;

import java.util.List;

public interface InventoryService {

    Inventory addNewInventory(Inventory inventory);

    Iterable<Inventory> addNewInventories(List<Inventory> inventories);

    int getInventoryOfClothes(long clothesId);

    List<Integer> getInventoriesOfListClothes(String clothesIds);

    List<Inventory> findAllInventories();

}
