package com.fetch.receiptProcessor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDTO {
    private String retailer;
    private String purchaseDate;
    private String purchaseTime;
    private List<ItemDTO> items;
    private String total;
}