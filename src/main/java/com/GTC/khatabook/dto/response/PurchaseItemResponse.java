package com.GTC.khatabook.dto.response;

import com.GTC.khatabook.enums.UnitType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class PurchaseItemResponse {

    private Long id;

    private String itemName;

    private BigDecimal quantity;

    private UnitType unit;

    private BigDecimal rate;

    private BigDecimal cgstPercentage;

    private BigDecimal cgstAmount;

    private BigDecimal sgstPercentage;

    private BigDecimal sgstAmount;

    private BigDecimal total;
}