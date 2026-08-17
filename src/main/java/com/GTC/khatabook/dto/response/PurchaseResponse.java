package com.GTC.khatabook.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PurchaseResponse {

    private Long id;

    private Long providerId;

    private String providerName;

    private String providerMobile;

    private LocalDate purchaseDate;

    private String remarks;

    private BigDecimal grandTotal;

    private List<PurchaseItemResponse> items;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}