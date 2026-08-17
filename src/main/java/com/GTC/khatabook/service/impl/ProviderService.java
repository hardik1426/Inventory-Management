package com.GTC.khatabook.service.impl;


import com.GTC.khatabook.dto.request.ProviderRequest;
import com.GTC.khatabook.dto.response.ProviderResponse;
import com.GTC.khatabook.entity.Provider;
import com.GTC.khatabook.exception.DuplicateResourceException;
import com.GTC.khatabook.exception.ResourceNotFoundException;
import com.GTC.khatabook.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProviderService {

    private final ProviderRepository providerRepository;

    public ProviderResponse createProvider(ProviderRequest request) {

        if (providerRepository.existsByMobile(request.getMobile())) {
            throw new DuplicateResourceException(
                    "Provider already exists with name: " + request.getName()
            );
        }

        Provider provider = Provider.builder()
                .name(request.getName().trim())
                .mobile(request.getMobile())
                .address(request.getAddress())
                .gstNumber(request.getGstNumber())
                .active(true)
                .build();

        Provider savedProvider = providerRepository.save(provider);

        return mapToResponse(savedProvider);
    }

    @Transactional(readOnly = true)
    public List<ProviderResponse> getAllProviders() {

        return providerRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProviderResponse getProviderById(Long id) {

        Provider provider = providerRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Provider not found with id: " + id));
        return mapToResponse(provider);
    }

    public ProviderResponse updateProvider(
            Long id,
            ProviderRequest request) {

        Provider provider = providerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Provider not found with id: " + id));

        provider.setName(request.getName().trim());
        provider.setMobile(request.getMobile());
        provider.setAddress(request.getAddress());
        provider.setGstNumber(request.getGstNumber());

        Provider updatedProvider = providerRepository.save(provider);

        return mapToResponse(updatedProvider);
    }

    public void deleteProvider(Long id) {

        Provider provider = providerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Provider not found with id: " + id));

        /*
         * We are doing a soft delete.
         * The database record remains, but it becomes inactive.
         */
        provider.setActive(false);

        providerRepository.save(provider);
    }

    private ProviderResponse mapToResponse(Provider provider) {

        return ProviderResponse.builder()
                .id(provider.getId())
                .name(provider.getName())
                .mobile(provider.getMobile())
                .address(provider.getAddress())
                .gstNumber(provider.getGstNumber())
                .active(provider.getActive())
                .createdAt(provider.getCreatedAt())
                .updatedAt(provider.getUpdatedAt())
                .build();
    }
}