package com.GTC.khatabook.dto.response;

import com.GTC.khatabook.enums.UnitType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ItemResponse {

    private Long id;
    private String name;
    private UnitType defaultUnit;
    private String description;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}