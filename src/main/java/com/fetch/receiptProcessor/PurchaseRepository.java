package com.fetch.receiptProcessor.Repository;

import com.fetch.receiptProcessor.Entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {

}