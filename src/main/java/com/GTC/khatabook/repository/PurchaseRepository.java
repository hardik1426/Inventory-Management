package com.GTC.khatabook.repository;

import com.GTC.khatabook.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}