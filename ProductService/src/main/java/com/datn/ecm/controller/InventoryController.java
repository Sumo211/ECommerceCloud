package com.datn.ecm.controller;

import com.datn.ecm.model.Inventory;
import com.datn.ecm.service.inventory.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/inventories")
public class InventoryController {

    private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping()
    public ResponseEntity addNewInventory(@RequestBody Inventory inventory) {
        inventoryService.addNewInventory(inventory);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping()
    public ResponseEntity findAllInventories() {
        List<Inventory> inventories = inventoryService.findAllInventories();

        if(inventories.isEmpty()) {
            throw new RuntimeException("can not found inventory");
        }

        return ResponseEntity.status(HttpStatus.OK).body(inventories);
    }

    @PostMapping(value = "/list")
    public ResponseEntity addNewInventories(@RequestBody List<Inventory> inventories) {
        inventoryService.addNewInventories(inventories);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/{clothesIds}")
    public ResponseEntity<List<Integer>> statisticProduct(@PathVariable String clothesIds) {
        List<Integer> currentState = inventoryService.getInventoriesOfListClothes(clothesIds);
        return ResponseEntity.status(HttpStatus.OK).body(currentState);
    }

    @GetMapping(value = "/amount/{clothesId}")
    public ResponseEntity getAmountOfClothes(@PathVariable long clothesId) {
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.getInventoryOfClothes(clothesId));
    }

}
