package com.fetch.receiptProcessor.Controller;


import com.fetch.receiptProcessor.Entity.Purchase;
import com.fetch.receiptProcessor.Repository.PurchaseRepository;
import com.fetch.receiptProcessor.Service.PurchaseService;
import com.fetch.receiptProcessor.dto.IdDTO;
import com.fetch.receiptProcessor.dto.PointsDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("receipt")
@RequiredArgsConstructor
public class ProcessorController {

    public ProcessorController(PurchaseService purchaseService){
        this.purchaseService =purchaseService;
    }

    @Autowired
    private PurchaseService purchaseService;
    @PostMapping("/process")
    public ResponseEntity<IdDTO> addPurchase(@RequestBody @NonNull Purchase dto){
        return ResponseEntity.ok().body(IdDTO.builder().id(purchaseService.processReceipt(dto)).build());
    }


    @GetMapping("/{id}/points")
    public ResponseEntity<PointsDTO> getPoints(@PathVariable @NonNull UUID id){
        ResponseEntity<PointsDTO> result = null;
        try{
            result =  ResponseEntity.ok().body(purchaseService.getPoints(id));
        }
        catch (Exception e) {
            // Exception will be handled by the ErrorHandlingAspect
        }
        return  result;
    }

}