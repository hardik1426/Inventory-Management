package com.GTC.khatabook.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProviderResponse {

    private Long id;
    private String name;
    private String mobile;
    private String address;
    private String gstNumber;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}