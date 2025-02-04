package com.fetch.receiptProcessor.Service;

import com.fetch.receiptProcessor.Entity.Item;
import com.fetch.receiptProcessor.Entity.Purchase;
import com.fetch.receiptProcessor.dto.PointsDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

//Rules
//        These rules collectively define how many points should be awarded to a receipt.
//
//        One point for every alphanumeric character in the retailer name.
//        50 points if the total is a round dollar amount with no cents.
//        25 points if the total is a multiple of 0.25.
//        5 points for every two items on the receipt.
//        If the trimmed length of the item description is a multiple of 3, multiply the price by 0.2 and round up to the nearest integer. The result is the number of points earned.
//        6 points if the day in the purchase date is odd.
//        10 points if the time of purchase is after 2:00pm and before 4:00pm.

@Service
public class CalculatorService {
    private final int alphanumericPoint = 1;
    private final int roundDollarPoints = 50;
    private final int multipleOf025Points = 25;
    private final int everyTwoItemsPoints = 5;
    private final double lengthOfItemDescriptionPoints = 0.2;
    private final int oddDatePoints = 6;
    private final int timeOfPurchasePoints = 10;

    public int countCharacters(String input ){
        int count=0;
        for (int i = 0; i < input.length(); i++) {
            if (Character.isLetter(input.charAt(i)) ||Character.isDigit(input.charAt(i)))
                count++;
        }
        return  count;
    }

    public int calculateAlphanumericPoint(String retailerName) {
        return countCharacters(retailerName.replaceAll("[^A-Za-z0-9 ]", " ").trim()) * alphanumericPoint;
    }

    public int calculateRoundedDollarAmountWithNoCents(double total){
        if(total % 1==0)
        {
            return roundDollarPoints;
        }
        else {
            return 0;
        }
    }

    public int calculateMultipleOf025(double total){
        if (total%0.25 ==0) {
            return multipleOf025Points;
        }
        else {
            return 0;
        }
    }

    public int calculateEveryTwoItemsOnTheReceipt(int size){
        return (size/2)* everyTwoItemsPoints;
    }

    public int calculateLengthOfTheItemDescription (Purchase purchaseEntity){
        int total =0;
        for (Item item: purchaseEntity.getItems()) {
            if(item.getShortDescription().trim().length()%3==0){
                total= total+ (int) Math.ceil (item.getPrice()* lengthOfItemDescriptionPoints);
            }
        }
        return  total;
    }


    public int calculateOnPurchaseDate (String purchaseDate){
        LocalDate date = LocalDate.parse(purchaseDate, DateTimeFormatter.ISO_DATE);
        if(date.getDayOfMonth() % 2==1){
            return oddDatePoints;
        }
        else{
            return 0;
        }
    }

    public int calculateTimeOfPurchase (String purchaseTime){
        LocalTime startTime = LocalTime.parse( "14:00:00" );
        LocalTime stopTime = LocalTime.parse( "16:00:00" );
        LocalTime time = LocalTime.parse( purchaseTime);
        if( time.isAfter(startTime) && time.isBefore(stopTime)) {
            return timeOfPurchasePoints;
        }else {
            return 0;
        }
    }

    public PointsDTO calculatePoints(Purchase purchaseEntity) {
        int totalPoints =0;
        totalPoints += calculateAlphanumericPoint(purchaseEntity.getRetailer());
        totalPoints += calculateRoundedDollarAmountWithNoCents(purchaseEntity.getTotal());
        totalPoints += calculateMultipleOf025(purchaseEntity.getTotal());
        totalPoints +=calculateEveryTwoItemsOnTheReceipt(purchaseEntity.getItems().size());
        totalPoints +=calculateLengthOfTheItemDescription(purchaseEntity);
        totalPoints +=calculateOnPurchaseDate(purchaseEntity.getPurchaseDate());
        totalPoints +=calculateTimeOfPurchase(purchaseEntity.getPurchaseTime());
        return  PointsDTO.builder().points(totalPoints).build();
    }

}