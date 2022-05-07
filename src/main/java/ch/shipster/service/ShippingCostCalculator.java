package ch.shipster.service;


import ch.shipster.data.domain.Provider;

// Manuel

//Object required to store articles from Basket in Array with additional information from Articles
class ShippingCostObject {
    int ArticleId;
    int quantity;
    float palletspace;
    float maxstack;
    float PalletProductRatio;

    public ShippingCostObject(int articleId, int quantity, float palletspace, float maxstack, float palletProductRatio) {
        ArticleId = articleId;
        this.quantity = quantity;
        this.palletspace = palletspace;
        this.maxstack = maxstack;
        PalletProductRatio = (palletspace / maxstack);
    }
}

public class ShippingCostCalculator {
    int OrderId;
    float minPalletSpace;
    int requiredPallet;
    float requiredTotalSpace;
    int HashTableSize;
    ShippingCostObject sco[] = new ShippingCostObject[HashTableSize];

    //create total sum of requiredTotalSpace
    private float requiredSpace(){
        requiredTotalSpace = 0;
        for (int i = 0; i < sco.length; i++) {
            requiredTotalSpace = requiredTotalSpace + (sco[i].quantity * sco[i].PalletProductRatio);
        }
        return requiredTotalSpace;
    }

    //find max value of min required pallets
   private float minRequiredPallet(){
       minPalletSpace = 0;
        for (int i = 0; i < sco.length; i++) {
            if (minPalletSpace < sco[i].palletspace) {
                minPalletSpace = sco[i].palletspace;
            }
       }
       return minPalletSpace;
   }

    //Calculate amount of required pallets
   private int requiredPallets(float requiredTotalSpace, float minPalletSpace) {
        this.requiredTotalSpace = requiredTotalSpace;
        this.minPalletSpace = minPalletSpace;

        while (requiredTotalSpace < minPalletSpace) {
            minPalletSpace = (minPalletSpace * 2);
        }
       requiredPallet = (int) Math.ceil(minPalletSpace);
       return requiredPallet;
        }

    public static float calculateShippingCost(Integer distance, Integer pallets, Provider shippingProvider) {
        float shippingCost = 1.0F;


        // TODO: Calculate shipping cost

        return shippingCost;
    }
}
