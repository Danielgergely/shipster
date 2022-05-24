package ch.shipster.service;

import ch.shipster.data.domain.Address;
import ch.shipster.data.domain.Article;
import ch.shipster.data.domain.OrderItem;

import org.jetbrains.annotations.NotNull;
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

    public float palletCalculation(Long orderId) {
        List<OrderItem> sco = orderService.getOrderItems(orderId);
        return requiredPallets(sco);
    }

    public boolean spaceLimit(float requiredPallets) {
        if (requiredPallets >= 12) {
            return false;
        } else {
            return false;
        }
    }

    public float costCalculation(Long orderId) throws IOException, InterruptedException {
        Address currentAddress = orderService.getUserAddress(orderId);
        List<OrderItem> sco = orderService.getOrderItems(orderId);
        int requiredPallets = requiredPallets(sco);
        int distance = DistanceCalculator.calculateDistance(currentAddress);

        return costService.getCheapestCost(distance, requiredPallets).getPrice();
    }

    public float costCalculation(Long orderId, Long providerId) throws IOException, InterruptedException {
        Address currentAddress = orderService.getUserAddress(orderId);
        List<OrderItem> sco = orderService.getOrderItems(orderId);
        int requiredPallets = requiredPallets(sco);
        int distance = DistanceCalculator.calculateDistance(currentAddress);
        return costService.getCost(providerId, distance, requiredPallets).getPrice();
    }

    private int requiredPallets(List<OrderItem> basketItems){
        float palletsRequired = 0;
        float spaceLeft;
        float tempMinPalletSpace;
        OrderItem currentBasketItem;
        Article currentArticle;

        while (!basketItems.isEmpty()){
            currentBasketItem = findLargestItem(basketItems);
            currentArticle = orderItemService.getArticle(currentBasketItem);
            tempMinPalletSpace = currentArticle.getPalletSpace();

            if (tempMinPalletSpace < currentBasketItem.getQuantity() * currentArticle.getPalletProductRatio()){
                tempMinPalletSpace = tempMinPalletSpace + currentArticle.getPalletSpace();
            }
            palletsRequired = palletsRequired + tempMinPalletSpace;
            spaceLeft = tempMinPalletSpace - (currentBasketItem.getQuantity() * currentArticle.getPalletProductRatio());
            basketItems.remove(currentBasketItem);

            if (!basketItems.isEmpty()){
                for (OrderItem i : basketItems){
                    float spaceRequired = (i.getQuantity() * orderItemService.getArticle(i).getPalletProductRatio());
                    if (spaceLeft <= spaceRequired){
                        palletsRequired = palletsRequired + spaceRequired;
                        basketItems.remove(i);
                    }
                }
            }
        }
        return (int) Math.ceil(palletsRequired);
    }

    private OrderItem findLargestItem(List<OrderItem> basketItems){
        float minPalletSpace = 0;
        Article currentArticle;
        OrderItem largestBasketItem = null;
        for (OrderItem i : basketItems) {
            currentArticle = orderItemService.getArticle(i);
            if (minPalletSpace < currentArticle.getPalletSpace()) {
                minPalletSpace = currentArticle.getPalletSpace();
                largestBasketItem = i;
            }
        }
        return largestBasketItem;
    }

    /*
    private int requiredPallets(List<OrderItem> sco) {
        float palletsRequired = 0;
        float spaceLeft = 0;
        float tempMinPalletSpace = 0;
        while (sco.isEmpty() == false)
        for (OrderItem i : sco) {
            Article currentArticle = orderItemService.getArticle(i);
            if (tempMinPalletSpace < currentArticle.getPalletSpace()) {
                tempMinPalletSpace = currentArticle.getPalletSpace();
            } else {
                palletsRequired = palletsRequired + tempMinPalletSpace;
                spaceLeft = palletsRequired - (i.getQuantity() * orderItemService.getArticle(i).getPalletProductRatio());
                sco.remove(i);
            }
            palletsRequired = palletsRequired + palletsRequired(sco, i, spaceLeft, palletsRequired);
        }
        return (int) Math.ceil(palletsRequired);
    }


    public float palletsRequired(List<OrderItem> orderItems, OrderItem oI, float spaceLeft, float palletsRequired) {
        for (OrderItem orderItem : orderItems) {
            float spaceNeeded = orderItem.getQuantity() * orderItemService.getArticle(oI).getPalletProductRatio();
            if (spaceLeft <= spaceNeeded) {
                palletsRequired = palletsRequired + spaceNeeded;
            }
        }
        return palletsRequired;
    }

     */
}

