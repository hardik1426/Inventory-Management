package com.GTC.khatabook.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {

    @NotBlank(message = "Customer name is required")
    @Size(max = 150, message = "Customer name cannot exceed 150 characters")
    private String name;

    @NotBlank(message = "Mobile number is required")
    @Size(max = 20, message = "Mobile number cannot exceed 20 characters")
    private String mobile;

    @Size(max = 500, message = "Address cannot exceed 500 characters")
    private String address;

    @Size(max = 20, message = "GST number cannot exceed 20 characters")
    private String gstNumber;
}