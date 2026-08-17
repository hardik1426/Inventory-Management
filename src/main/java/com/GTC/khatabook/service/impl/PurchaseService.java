package com.GTC.khatabook.service.impl;


import com.GTC.khatabook.dto.request.PurchaseItemRequest;
import com.GTC.khatabook.dto.request.PurchaseRequest;
import com.GTC.khatabook.dto.response.PurchaseItemResponse;
import com.GTC.khatabook.dto.response.PurchaseResponse;
import com.GTC.khatabook.entity.Item;
import com.GTC.khatabook.entity.Provider;
import com.GTC.khatabook.entity.Purchase;
import com.GTC.khatabook.entity.PurchaseItem;
import com.GTC.khatabook.exception.ResourceNotFoundException;
import com.GTC.khatabook.repository.ItemRepository;
import com.GTC.khatabook.repository.ProviderRepository;
import com.GTC.khatabook.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ProviderRepository providerRepository;
    private final ItemRepository itemRepository;

    public PurchaseResponse createPurchase(PurchaseRequest request) {

        // 1. Find provider using mobile number
        String mobile = request.getProviderMobile().trim();

        Provider provider = providerRepository
                .findByMobile(mobile)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Provider not found with mobile: " + mobile
                        )
                );

        // Don't allow purchases against an inactive provider
        if (!Boolean.TRUE.equals(provider.getActive())) {
            throw new ResourceNotFoundException(
                    "Provider is inactive with mobile: " + mobile
            );
        }

        // 2. Create Purchase
        Purchase purchase = Purchase.builder()
                .provider(provider)
                .purchaseDate(request.getPurchaseDate())
                .remarks(request.getRemarks())
                .grandTotal(BigDecimal.ZERO)
                .items(new ArrayList<>())
                .build();

        BigDecimal grandTotal = BigDecimal.ZERO;

        // 3. Process every purchase item
        for (PurchaseItemRequest itemRequest : request.getItems()) {

            String itemName = itemRequest.getItemName().trim();

            Item item = itemRepository
                    .findByNameIgnoreCase(itemName)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Item not found with name: " + itemName
                            )
                    );

            // Don't allow inactive items
            if (!Boolean.TRUE.equals(item.getActive())) {
                throw new ResourceNotFoundException(
                        "Item is inactive with name: " + itemName
                );
            }

            PurchaseItem purchaseItem =
                    createPurchaseItem(item, itemRequest);

            purchase.addItem(purchaseItem);

            grandTotal = grandTotal.add(
                    purchaseItem.getTotal()
            );
        }

        // 4. Set final grand total
        purchase.setGrandTotal(
                grandTotal.setScale(2, RoundingMode.HALF_UP)
        );

        // 5. Save purchase + purchase items
        Purchase savedPurchase =
                purchaseRepository.save(purchase);

        return mapToResponse(savedPurchase);
    }

    private PurchaseItem createPurchaseItem(
            Item item,
            PurchaseItemRequest request) {

        BigDecimal quantity = request.getQuantity();

        BigDecimal rate = request.getRate();

        BigDecimal cgstPercentage =
                request.getCgstPercentage();

        BigDecimal sgstPercentage =
                request.getSgstPercentage();

        // quantity × rate
        BigDecimal baseAmount = quantity
                .multiply(rate)
                .setScale(2, RoundingMode.HALF_UP);

        // base × CGST / 100
        BigDecimal cgstAmount = baseAmount
                .multiply(cgstPercentage)
                .divide(
                        BigDecimal.valueOf(100),
                        2,
                        RoundingMode.HALF_UP
                );

        // base × SGST / 100
        BigDecimal sgstAmount = baseAmount
                .multiply(sgstPercentage)
                .divide(
                        BigDecimal.valueOf(100),
                        2,
                        RoundingMode.HALF_UP
                );

        // base + CGST + SGST
        BigDecimal total = baseAmount
                .add(cgstAmount)
                .add(sgstAmount)
                .setScale(2, RoundingMode.HALF_UP);

        return PurchaseItem.builder()
                .item(item)
                .quantity(quantity)
                .unit(request.getUnit())
                .rate(rate)
                .cgstPercentage(cgstPercentage)
                .cgstAmount(cgstAmount)
                .sgstPercentage(sgstPercentage)
                .sgstAmount(sgstAmount)
                .total(total)
                .build();
    }

    @Transactional(readOnly = true)
    public PurchaseResponse getPurchaseById(Long id) {

        Purchase purchase = purchaseRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Purchase not found with id: " + id
                        )
                );

        return mapToResponse(purchase);
    }

    @Transactional(readOnly = true)
    public List<PurchaseResponse> getAllPurchases() {

        return purchaseRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private PurchaseResponse mapToResponse(
            Purchase purchase) {

        List<PurchaseItemResponse> items =
                purchase.getItems()
                        .stream()
                        .map(this::mapToItemResponse)
                        .toList();

        return PurchaseResponse.builder()
                .id(purchase.getId())
                .providerId(purchase.getProvider().getId())
                .providerName(purchase.getProvider().getName())
                .providerMobile(purchase.getProvider().getMobile())
                .purchaseDate(purchase.getPurchaseDate())
                .remarks(purchase.getRemarks())
                .grandTotal(purchase.getGrandTotal())
                .items(items)
                .createdAt(purchase.getCreatedAt())
                .updatedAt(purchase.getUpdatedAt())
                .build();
    }

    private PurchaseItemResponse mapToItemResponse(
            PurchaseItem purchaseItem) {

        return PurchaseItemResponse.builder()
                .id(purchaseItem.getId())
                .itemName(purchaseItem.getItem().getName())
                .quantity(purchaseItem.getQuantity())
                .unit(purchaseItem.getUnit())
                .rate(purchaseItem.getRate())
                .cgstPercentage(
                        purchaseItem.getCgstPercentage()
                )
                .cgstAmount(
                        purchaseItem.getCgstAmount()
                )
                .sgstPercentage(
                        purchaseItem.getSgstPercentage()
                )
                .sgstAmount(
                        purchaseItem.getSgstAmount()
                )
                .total(purchaseItem.getTotal())
                .build();
    }
}