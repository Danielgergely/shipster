package ch.shipster.service;

import ch.shipster.data.domain.Address;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


// Jonas
public class DistanceCalculator {

    public static Address warehouseAddress = new Address("Bahnhofstrasse", "6", "Windisch", "5210", "Switzerland");

    public static int calculateDistance(Address deliveryAddress) throws IOException, InterruptedException {
      String[] deliveryCoordinates = getCoordinates(deliveryAddress);
      String[] warehouseCoordinates = getCoordinates(warehouseAddress);
      return getDistance(warehouseCoordinates, deliveryCoordinates);
    }

    public static int getDistance(String[] coordinates1, String[] coordinates2) throws IOException, InterruptedException {

        String baseURL = "https://dev.virtualearth.net/REST/v1/Routes/DistanceMatrix?key=AvoMg355hzmFo7_Z3oXH0rlIMbBG2GQPM9kJVOpxMvpa2UaiVxm61yKNzxKJc6ks";
        String origin = "&origins="+coordinates1[0]+","+coordinates1[1];
        String destination = "&destinations="+coordinates2[0]+","+coordinates2[1];
        String travelMode= "&travelMode=driving";
        String APIcall = baseURL+origin+destination+travelMode;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(APIcall))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        String outputAPI = response.body();
               String distance = outputAPI;
        distance = outputAPI.substring(distance.indexOf("travelDistance")+16, distance.indexOf("travelDuration")-2);
        float finaldistance = Float.parseFloat(distance);

        return Math.round(finaldistance);
    }

    public static String[] getCoordinates(Address address) throws IOException, InterruptedException {
        String UserStreet = address.getStreet();
        String UserAddressNumber = address.getNumber();
        String UserZIP = address.getZip();
        String UserCity = address.getCity();
        String UserCountry = address.getCountry();
        String StreetAndStreetnum = UserStreet+" "+UserAddressNumber;
        StreetAndStreetnum = StreetAndStreetnum.replaceAll(" ", "%20");
        String APIpart1 = "http://dev.virtualearth.net/REST/v1/Locations?countryRegion=";
        String APIpart2 = "&postalCode=";
        String APIpart3 = "&addressLine=";
        String APIpart4 = "&key=AvoMg355hzmFo7_Z3oXH0rlIMbBG2GQPM9kJVOpxMvpa2UaiVxm61yKNzxKJc6ks";
        String APIcall = APIpart1+UserCountry+APIpart2+UserZIP+APIpart3+StreetAndStreetnum+APIpart4;
        APIcall = APIcall.replaceAll(" ", "%20");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(APIcall))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        String outputAPI = response.body();
        String long_latitude = outputAPI.substring(outputAPI.indexOf("geocodePoints")+14,
                outputAPI.indexOf("calculationMethod")-2);
        String latitude = long_latitude.substring(long_latitude.indexOf("coordinates")+14);
        latitude = latitude.substring(0,
                latitude.indexOf(","));
        String longitude = long_latitude.substring(long_latitude.indexOf("coordinates")+14);
        longitude = longitude.substring(longitude.indexOf(",")+1, longitude.indexOf("]"));
        return new String[]{latitude, longitude};
    }

     public static boolean validateAddress(Address deliveryAddress) throws IOException, InterruptedException {
        boolean isAddressValid = false;
        String UserStreet = deliveryAddress.getStreet();
        String UserAddressNumber = deliveryAddress.getNumber();
        String UserZIP = deliveryAddress.getZip();
        String UserCity = deliveryAddress.getCity();
        String UserCountry = deliveryAddress.getCountry();
        String StreetAndStreetnum = UserStreet+" "+UserAddressNumber;
        StreetAndStreetnum = StreetAndStreetnum.replaceAll(" ", "%20");
        String APIpart1 = "http://dev.virtualearth.net/REST/v1/Locations?countryRegion=";
        String APIpart2 = "&postalCode=";
        String APIpart3 = "&addressLine=";
        String APIpart4 = "&key=AvoMg355hzmFo7_Z3oXH0rlIMbBG2GQPM9kJVOpxMvpa2UaiVxm61yKNzxKJc6ks";
        String APIcall = APIpart1+UserCountry+APIpart2+UserZIP+APIpart3+StreetAndStreetnum+APIpart4;
        APIcall = APIcall.replaceAll(" ", "%20");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(APIcall))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        String outputAPI = response.body();
        try {
            String confidence = outputAPI.substring(outputAPI.indexOf("confidence") + 13,
                    outputAPI.indexOf("entityType") - 3);
            if (confidence.equals("High")){
                isAddressValid = true;
            }
        }
        catch (Exception e) {
            isAddressValid = false;
        }
        return isAddressValid;
    }
}
