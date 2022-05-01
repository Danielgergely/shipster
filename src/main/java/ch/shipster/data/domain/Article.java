package ch.shipster.data.domain;

public class Article {

    String name;
    float price;
    //array image
    float palletspace;
    int maxStack;

    public float getPalletspace() {
        return palletspace;
    }

    public float getMaxStack() {
        return maxStack;
    }
}
