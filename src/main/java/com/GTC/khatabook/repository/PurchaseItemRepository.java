package com.GTC.khatabook.repository;

import com.GTC.khatabook.entity.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseItemRepository
        extends JpaRepository<PurchaseItem, Long> {
}