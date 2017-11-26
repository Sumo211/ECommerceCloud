package com.datn.ecm.repository.inventory;

import com.datn.ecm.model.Inventory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InventoryRepository extends CrudRepository<Inventory, Long> {

    List<Inventory> findByClothesId(long clothesId);

}
