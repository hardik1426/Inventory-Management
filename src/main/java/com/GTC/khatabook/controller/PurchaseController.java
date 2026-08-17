package com.GTC.khatabook.controller;


import com.GTC.khatabook.dto.request.PurchaseRequest;
import com.GTC.khatabook.dto.response.PurchaseResponse;
import com.GTC.khatabook.service.impl.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")

public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<PurchaseResponse> createPurchase(@Valid @RequestBody PurchaseRequest request) {

        PurchaseResponse response = purchaseService.createPurchase(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PurchaseResponse>> getAllPurchases() {

        return ResponseEntity.ok(purchaseService.getAllPurchases());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseResponse> getPurchaseById(@PathVariable Long id) {

        return ResponseEntity.ok(purchaseService.getPurchaseById(id));
    }
}