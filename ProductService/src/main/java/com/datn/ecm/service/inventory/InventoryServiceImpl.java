package com.datn.ecm.service.inventory;

import com.datn.ecm.model.Inventory;
import com.datn.ecm.repository.inventory.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Inventory addNewInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Override
    public Iterable<Inventory> addNewInventories(List<Inventory> inventories) {
        return inventoryRepository.save(inventories);
    }

    @Override
    public int getInventoryOfClothes(long clothesId) {
        List<Inventory> inventories = inventoryRepository.findByClothesId(clothesId);
        return inventories.stream().mapToInt(inv -> (inv.getQuantity() * inv.getStatus().getValue())).sum();
    }

    @Override
    public List<Integer> getInventoriesOfListClothes(String clothesIds) {
        String[] productIdArray = clothesIds.split(",");
        List<Integer> result = new ArrayList<>();

        for (String clothesId : productIdArray) {
            result.add(getInventoryOfClothes(Long.valueOf(clothesId)));
        }

        return result;
    }

    @Override
    public List<Inventory> findAllInventories() {
        List<Inventory> inventories = new ArrayList<>();
        inventoryRepository.findAll().forEach(inventories::add);
        return inventories;
    }

}
