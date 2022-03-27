package ch.shipster.service;

import ch.shipster.data.domain.Address;


// Jonas
public class DistanceCalculator {

    Address warehouseAddress = new Address("Bahnhofstrasse", "6", "Windisch", "5210", "Switzerland");

    public static int calculateDistance(Address delivaryAddress) {
        Integer distance = 1;

        // TODO: call google API and calculate distance between warehouse and to address

        return distance;
    }
}
