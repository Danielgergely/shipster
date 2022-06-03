package ch.shipster.service;

import ch.shipster.data.domain.Address;
import ch.shipster.data.domain.Article;
import ch.shipster.data.domain.OrderItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
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
        List<OrderItem> basketItems = orderService.getOrderItems(orderId);
        return requiredPallets(basketItems);
    }

    public boolean spaceLimit(float requiredPallets) {
        return (requiredPallets <= 12);
    }

    public float costCalculation(Long orderId) throws IOException, InterruptedException {
        Address currentAddress = orderService.getUserAddress(orderId);
        List<OrderItem> basketItems = orderService.getOrderItems(orderId);
        int requiredPallets = requiredPallets(basketItems);
        int distance = DistanceCalculator.calculateDistance(currentAddress);

        return costService.getCheapestCost(distance, requiredPallets).getPrice();
    }

    public float costCalculation(Long orderId, Long providerId) throws IOException, InterruptedException {
        Address currentAddress = orderService.getUserAddress(orderId);
        List<OrderItem> basketItems = orderService.getOrderItems(orderId);
        int requiredPallets = requiredPallets(basketItems);
        int distance = DistanceCalculator.calculateDistance(currentAddress);
        return costService.getCost(providerId, distance, requiredPallets).getPrice();
    }

    public int requiredPallets(List<OrderItem> basketItems) {
        float palletsRequired = 0;
        float spaceLeft;
        float tempMinPalletSpace;
        OrderItem currentBasketItem;
        Article currentArticle;

        while (!basketItems.isEmpty()) {
            currentBasketItem = findLargestItem(basketItems);
            currentArticle = orderItemService.getArticle(currentBasketItem);
            tempMinPalletSpace = currentArticle.getPalletSpace();

            while (tempMinPalletSpace < currentBasketItem.getQuantity() * currentArticle.getPalletProductRatio()) {
                tempMinPalletSpace = tempMinPalletSpace + currentArticle.getPalletSpace();
            }
            palletsRequired = palletsRequired + tempMinPalletSpace;
            spaceLeft = tempMinPalletSpace - (currentBasketItem.getQuantity() * currentArticle.getPalletProductRatio());
            basketItems.remove(currentBasketItem);

            if (!basketItems.isEmpty()) {
                List toRemove = new ArrayList();
                for (OrderItem i : basketItems) {
                    float spaceRequired = (i.getQuantity() * orderItemService.getArticle(i).getPalletProductRatio());
                    if (spaceLeft >= spaceRequired) {
                        spaceLeft = spaceLeft - spaceRequired;
                        toRemove.add(i);
                    }
                }
                basketItems.removeAll(toRemove);
            }
        }
        return (int) Math.ceil(palletsRequired);
    }

    private OrderItem findLargestItem(List<OrderItem> basketItems) {
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
}

