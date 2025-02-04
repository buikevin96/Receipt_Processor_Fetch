package com.fetch.receiptProcessor.Repository;

import com.fetch.receiptProcessor.Entity.Item;
import com.fetch.receiptProcessor.Entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
    List<Item> findByPurchase(Purchase savedPurchase);
}