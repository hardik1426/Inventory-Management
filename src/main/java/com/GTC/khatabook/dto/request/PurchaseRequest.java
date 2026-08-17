package com.GTC.khatabook.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PurchaseRequest {

    @NotBlank(message = "Provider mobile is required")
    private String providerMobile;

    @NotNull(message = "Purchase date is required")
    private LocalDate purchaseDate;

    private String remarks;

    @NotEmpty(message = "At least one purchase item is required")
    @Valid
    private List<PurchaseItemRequest> items;
}