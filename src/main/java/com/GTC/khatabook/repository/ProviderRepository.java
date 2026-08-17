package com.GTC.khatabook.repository;

import com.GTC.khatabook.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProviderRepository extends JpaRepository<Provider, Long> {

    List<Provider> findByActiveTrue();

    boolean existsByMobile(String mobile);

    Optional<Provider> findByMobile(String mobile);

    boolean existsByMobileAndIdNot(String mobile, Long id);
}