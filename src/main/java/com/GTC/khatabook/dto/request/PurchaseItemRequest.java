package com.GTC.khatabook.dto.request;

import com.GTC.khatabook.enums.UnitType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PurchaseItemRequest {

    @NotBlank(message = "Item name is required")
    private String itemName;

    @NotNull(message = "Quantity is required")
    @DecimalMin(
            value = "0.001",
            message = "Quantity must be greater than zero"
    )
    private BigDecimal quantity;

    @NotNull(message = "Unit is required")
    private UnitType unit;

    @NotNull(message = "Rate is required")
    @DecimalMin(
            value = "0.00",
            message = "Rate cannot be negative"
    )
    private BigDecimal rate;

    @NotNull(message = "CGST percentage is required")
    @DecimalMin(
            value = "0.00",
            message = "CGST percentage cannot be negative"
    )
    private BigDecimal cgstPercentage;

    @NotNull(message = "SGST percentage is required")
    @DecimalMin(
            value = "0.00",
            message = "SGST percentage cannot be negative"
    )
    private BigDecimal sgstPercentage;
}