package com.GTC.khatabook.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {

    private Long id;

    private String name;

    private String mobile;

    private String address;

    private String gstNumber;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}