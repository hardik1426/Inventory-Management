package com.GTC.khatabook.controller;




import com.GTC.khatabook.dto.request.ProviderRequest;
import com.GTC.khatabook.dto.response.ProviderResponse;
import com.GTC.khatabook.service.impl.ProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/providers")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ProviderController {

    private static final Logger log = LoggerFactory.getLogger(ProviderController.class);
    private final ProviderService providerService;

    @PostMapping
    public ResponseEntity<ProviderResponse> createProvider(@Valid @RequestBody ProviderRequest request) {

        log.info("Entered into create provider: {}",request);
        ProviderResponse response = providerService.createProvider(request);
        log.info("Finished create provider api: {}",response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProviderResponse>> getAllProviders() {
        log.info("Entered into getAllProviders provider");
        return ResponseEntity.ok(providerService.getAllProviders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderResponse> getProviderById(@PathVariable Long id) {
        log.info("Entered into getProviderById provider: {}",id);
        return ResponseEntity.ok(providerService.getProviderById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderResponse> updateProvider(@PathVariable Long id, @Valid @RequestBody ProviderRequest request) {

        return ResponseEntity.ok(providerService.updateProvider(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {

        providerService.deleteProvider(id);

        return ResponseEntity.noContent().build();
    }
}