package ch.shipster.service;
import ch.shipster.data.domain.Address;
import ch.shipster.data.domain.Article;
import ch.shipster.data.domain.OrderItem;
import ch.shipster.service.CostService;


import ch.shipster.data.domain.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

// Manuel
@Service
public class ShippingCostCalculator {

    @Autowired
    CostService costService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    public float costCalculation (long orderid) throws IOException, InterruptedException {
        Address currentAddress = orderService.getUserAddress(orderid);
        List<OrderItem> sco = orderService.getOrderItems(orderid);
        float requiredTotalSpace = requiredSpace(sco);
        float minRequiredPallet = minRequiredPallet(sco);
        int requiredPallets = requiredPallets(requiredTotalSpace, minRequiredPallet);
        int distance = DistanceCalculator.calculateDistance(currentAddress);

        return costService.getCheapestCost(distance, requiredPallets).getPrice();
    }

    public float costCalculation (long orderid, Long providerId) throws IOException, InterruptedException {
        Address currentAddress = orderService.getUserAddress(orderid);
        List<OrderItem> sco = orderService.getOrderItems(orderid);
        float requiredTotalSpace = requiredSpace(sco);
        float minRequiredPallet = minRequiredPallet(sco);
        int requiredPallets = requiredPallets(requiredTotalSpace, minRequiredPallet);
        int distance = DistanceCalculator.calculateDistance(currentAddress);

        return costService.getCost(providerId, distance, requiredPallets).getPrice();
    }

    //create total sum of requiredTotalSpace
    private float requiredSpace(List<OrderItem> sco){
        float requiredTotalSpace = 0;
        for (OrderItem i : sco) {
            requiredTotalSpace = requiredTotalSpace + (i.getQuantity() * orderItemService.getArticle(i).getPalletProductRatio());
        }
        return requiredTotalSpace;
    }

    //find max value of min required pallets
   private float minRequiredPallet(List<OrderItem> sco){
       float minPalletSpace = 0;
       Article currentArticle;
        for (OrderItem i : sco) {
            currentArticle = orderItemService.getArticle(i);
            if (minPalletSpace < currentArticle.getPalletSpace()) {
                minPalletSpace = currentArticle.getPalletSpace();
            }
       }
       return minPalletSpace;
   }

    //Calculate amount of required pallets
   private int requiredPallets(float requiredTotalSpace, float minPalletSpace) {
        requiredTotalSpace = requiredTotalSpace;
        minPalletSpace = minPalletSpace;

        while (requiredTotalSpace < minPalletSpace) {
            minPalletSpace = (minPalletSpace * 2);
        }
       return (int)Math.ceil(minPalletSpace);
        }
}
