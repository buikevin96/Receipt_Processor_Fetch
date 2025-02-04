package com.fetch.receiptProcessor.Service;

import com.fetch.receiptProcessor.Entity.Item;
import com.fetch.receiptProcessor.Entity.Purchase;
import com.fetch.receiptProcessor.Repository.ItemRepository;
import com.fetch.receiptProcessor.Repository.PurchaseRepository;
import com.fetch.receiptProcessor.dto.PointsDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private ItemRepository itemRepository;


    @Transactional
    public UUID processReceipt(Purchase purchaseEntity) {

        try {
            Purchase entity = Purchase.builder()
                    .retailer(purchaseEntity.getRetailer())
                    .purchaseDate(purchaseEntity.getPurchaseDate())
                    .purchaseTime(purchaseEntity.getPurchaseTime())
                    .total(purchaseEntity.getTotal())
                    .build();

            Purchase purchase = purchaseRepository.save(entity);

            List<Item> items = purchaseEntity.getItems().stream()
                    .map(itemEntity -> Item.builder()
                            .shortDescription(itemEntity.getShortDescription())
                            .price(itemEntity.getPrice())
                            .purchase(purchase)
                            .build())
                    .collect(Collectors.toList());

            itemRepository.saveAll(items);
            return purchase.getId();
        } catch (Exception e) {
            // Handle the exception or rethrow it if needed
            throw e;
        }
    }

    public PointsDTO getPoints(UUID id) {
        CalculatorService calculator = new CalculatorService();
        Optional<Purchase> purchase = purchaseRepository.findById(id);
        return calculator.calculatePoints(purchase.orElseGet(null));

    }
    public  Optional<Purchase> getPurchase(UUID id) {
        Optional<Purchase> purchase = purchaseRepository.findById(id);
        return purchase;

    }

    public void setPurchaseRepository(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }
}