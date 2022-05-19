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

    public float costCalculation(long orderid) throws IOException, InterruptedException {
        Address currentAddress = orderService.getUserAddress(orderid);
        List<OrderItem> sco = orderService.getOrderItems(orderid);
        /*
        float requiredTotalSpace = requiredSpace(sco);
        float minRequiredPallet = minRequiredPallet(sco);
        int requiredPallets = requiredPallets(requiredTotalSpace, minRequiredPallet);
        */
        int requiredPallets = requiredPallets2(sco);
        //float distance = DistanceCalculator.calculateDistance(currentAddress);
        int distance = DistanceCalculator.calculateDistance(currentAddress);

        return costService.getCheapestCost(distance, requiredPallets).getPrice();
    }

    public float costCalculation(long orderid, Long providerId) throws IOException, InterruptedException {
        Address currentAddress = orderService.getUserAddress(orderid);
        List<OrderItem> sco = orderService.getOrderItems(orderid);
        /*
        float requiredTotalSpace = requiredSpace(sco);
        float minRequiredPallet = minRequiredPallet(sco);
        int requiredPallets = requiredPallets(requiredTotalSpace, minRequiredPallet);
        */
        int requiredPallets = requiredPallets2(sco);
        //float distance = DistanceCalculator.calculateDistance(currentAddress);
        int distance = DistanceCalculator.calculateDistance(currentAddress);

        return costService.getCost(providerId, distance, requiredPallets).getPrice();
    }

    /*
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
//        requiredTotalSpace = requiredTotalSpace;
//        minPalletSpace = minPalletSpace;

        while (requiredTotalSpace > 1) {
            minPalletSpace = (minPalletSpace * 2);
            requiredTotalSpace -= 1;
        }
       return (int)Math.ceil(minPalletSpace);
        }
*/

    //New version of pallet calculation
    private int requiredPallets2(List<OrderItem> sco) {
        float palletsRequired = 0;
        float spaceLeft = 0;
        float tempMinPalletSpace = 0;

        // while (sco == null){
        for (OrderItem i : sco) {
            Article currentArticle = orderItemService.getArticle(i);
            if (tempMinPalletSpace < currentArticle.getPalletSpace()) {
                tempMinPalletSpace = currentArticle.getPalletSpace();
            } else {
                palletsRequired = palletsRequired + tempMinPalletSpace;
                spaceLeft = palletsRequired - (i.getQuantity() * orderItemService.getArticle(i).getPalletProductRatio());
                sco.remove(i);
            }
            palletsRequired = palletsRequired(sco, i, spaceLeft);
//            for (OrderItem j : sco) {
//                //currentArticle = orderItemService.getArticle(j);
//                float spaceNeeded = j.getQuantity() * orderItemService.getArticle(i).getPalletProductRatio();
//                if (spaceLeft <= spaceNeeded) {
//                    palletsRequired = palletsRequired + (j.getQuantity() * orderItemService.getArticle(i).getPalletProductRatio());
//                    sco.remove(j);
//                }
//            }
        }
        //  }
        return (int) Math.ceil(palletsRequired);
    }


    public float palletsRequired(List<OrderItem> orderItems, OrderItem oI, float spaceLeft) {
        float palletsRequired = 0;
        for(OrderItem orderItem : orderItems){
            float spaceNeeded = orderItem.getQuantity() * orderItemService.getArticle(oI).getPalletProductRatio();
            if(spaceLeft <= spaceNeeded) {
                palletsRequired = palletsRequired + spaceNeeded;
            }
        }
        return palletsRequired;
    }
}
