package com.GTC.khatabook.repository;

import com.GTC.khatabook.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByActiveTrue();

    boolean existsByNameIgnoreCase(String name);

    Optional<Item> findByNameIgnoreCase(String name);
}