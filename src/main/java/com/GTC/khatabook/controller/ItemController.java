package com.GTC.khatabook.controller;


import com.GTC.khatabook.dto.request.ItemRequest;
import com.GTC.khatabook.dto.response.ItemResponse;
import com.GTC.khatabook.service.impl.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    /**
     * Create a new item.
     */
    @PostMapping
    public ResponseEntity<ItemResponse> createItem(
            @Valid @RequestBody ItemRequest request) {

        ItemResponse response =
                itemService.createItem(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Get all active items.
     */
    @GetMapping
    public ResponseEntity<List<ItemResponse>> getAllItems() {

        return ResponseEntity.ok(
                itemService.getAllItems()
        );
    }

    /**
     * Get item by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItemById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                itemService.getItemById(id)
        );
    }

    /**
     * Update item.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> updateItem(
            @PathVariable Long id,
            @Valid @RequestBody ItemRequest request) {

        return ResponseEntity.ok(
                itemService.updateItem(id, request)
        );
    }

    /**
     * Soft delete item.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable Long id) {

        itemService.deleteItem(id);

        return ResponseEntity.noContent().build();
    }
}