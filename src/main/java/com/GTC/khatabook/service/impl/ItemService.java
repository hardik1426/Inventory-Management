package com.GTC.khatabook.service.impl;


import com.GTC.khatabook.dto.request.ItemRequest;
import com.GTC.khatabook.dto.response.ItemResponse;
import com.GTC.khatabook.entity.Item;
import com.GTC.khatabook.exception.DuplicateResourceException;
import com.GTC.khatabook.exception.ResourceNotFoundException;
import com.GTC.khatabook.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * Create a new item.
     */
    public ItemResponse createItem(ItemRequest request) {

        String itemName = request.getName().trim();

        if (itemRepository.existsByNameIgnoreCase(itemName)) {
            throw new DuplicateResourceException(
                    "Item already exists with name: " + itemName
            );
        }

        Item item = Item.builder()
                .name(itemName)
                .defaultUnit(request.getDefaultUnit())
                .description(request.getDescription())
                .active(true)
                .build();

        Item savedItem = itemRepository.save(item);

        return mapToResponse(savedItem);
    }

    /**
     * Get all active items.
     */
    @Transactional(readOnly = true)
    public List<ItemResponse> getAllItems() {

        return itemRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Get item by ID.
     */
    @Transactional(readOnly = true)
    public ItemResponse getItemById(Long id) {

        Item item = itemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Item not found with id: " + id
                        )
                );

        return mapToResponse(item);
    }

    /**
     * Update an existing item.
     */
    public ItemResponse updateItem(
            Long id,
            ItemRequest request) {

        Item item = itemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Item not found with id: " + id
                        )
                );

        String itemName = request.getName().trim();

        /*
         * Check duplicate name only when the name
         * is actually being changed.
         */
        if (!item.getName().equalsIgnoreCase(itemName)
                && itemRepository.existsByNameIgnoreCase(itemName)) {

            throw new DuplicateResourceException(
                    "Item already exists with name: " + itemName
            );
        }

        item.setName(itemName);
        item.setDefaultUnit(request.getDefaultUnit());
        item.setDescription(request.getDescription());

        Item updatedItem = itemRepository.save(item);

        return mapToResponse(updatedItem);
    }

    /**
     * Soft delete an item.
     */
    public void deleteItem(Long id) {

        Item item = itemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Item not found with id: " + id
                        )
                );

        item.setActive(false);

        itemRepository.save(item);
    }

    /**
     * Convert Entity → Response DTO.
     */
    private ItemResponse mapToResponse(Item item) {

        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .defaultUnit(item.getDefaultUnit())
                .description(item.getDescription())
                .active(item.getActive())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }
}