package com.GTC.khatabook.dto.request;

import com.GTC.khatabook.enums.UnitType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequest {

    @NotBlank(message = "Item name is required")
    @Size(
            max = 150,
            message = "Item name cannot exceed 150 characters"
    )
    private String name;

    @NotNull(message = "Default unit is required")
    private UnitType defaultUnit;

    @Size(
            max = 500,
            message = "Description cannot exceed 500 characters"
    )
    private String description;
}