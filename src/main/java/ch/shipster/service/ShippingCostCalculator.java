package ch.shipster.service;

import ch.shipster.data.domain.Address;
import ch.shipster.data.domain.Article;
import ch.shipster.data.domain.OrderItem;

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

    private int requiredPallets(List<OrderItem> sco) {
        float palletsRequired = 0;
        float spaceLeft = 0;
        float tempMinPalletSpace = 0;

        for (OrderItem i : sco) {
            Article currentArticle = orderItemService.getArticle(i);
            if (tempMinPalletSpace < currentArticle.getPalletSpace()) {
                tempMinPalletSpace = currentArticle.getPalletSpace();
            } else {
                palletsRequired = palletsRequired + tempMinPalletSpace;
                spaceLeft = palletsRequired - (i.getQuantity() * orderItemService.getArticle(i).getPalletProductRatio());
                sco.remove(i);
            }
            palletsRequired = palletsRequired + palletsRequired(sco, i, spaceLeft);
        }
        return (int) Math.ceil(palletsRequired);
    }


    public float palletsRequired(List<OrderItem> orderItems, OrderItem oI, float spaceLeft) {
        float palletsRequired = 0;
        for (OrderItem orderItem : orderItems) {
            float spaceNeeded = orderItem.getQuantity() * orderItemService.getArticle(oI).getPalletProductRatio();
            if (spaceLeft <= spaceNeeded) {
                palletsRequired = palletsRequired + spaceNeeded;
            }
        }
        return palletsRequired;
    }
}
