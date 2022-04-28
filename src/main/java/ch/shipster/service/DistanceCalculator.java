package ch.shipster.service;

import ch.shipster.data.domain.Address;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


// Jonas
public class DistanceCalculator {

    public static Address warehouseAddress = new Address("Bahnhofstrasse", "6", "Windisch", "5210", "Switzerland");




    public static String calculateDistance(Address deliveryAddress) throws IOException, InterruptedException, JSONException {
        // TODO: call google API and calculate distance between warehouse and to address
      String[] deliveryCoordinates = getCoordinates(deliveryAddress);
      String[] warehouseCoordinates = getCoordinates(warehouseAddress);
      int distance = getDistance(warehouseCoordinates, deliveryCoordinates);
    return "replace me later";
    }

    public static int getDistance(String[] coordinates1, String[] coordinates2) throws IOException, InterruptedException {
        //deliveryCoordinates[0]+","+deliveryCoordinates[1]

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
        //output {"authenticationResultCode":"ValidCredentials","brandLogoUri":"http:\/\/dev.virtualearth.net\/Branding\/logo_powered_by.png","copyright":"Copyright © 2022 Microsoft and its suppliers. All rights reserved. This API cannot be accessed and the content and any results may not be used, reproduced or transmitted in any manner without express written permission from Microsoft Corporation.","resourceSets":[{"estimatedTotal":1,"resources":[{"__type":"DistanceMatrix:http:\/\/schemas.microsoft.com\/search\/local\/ws\/rest\/v1","destinations":[{"latitude":47.28534,"longitude":7.70362}],"origins":[{"latitude":47.48119,"longitude":8.21193}],"results":[{"destinationIndex":0,"originIndex":0,"totalWalkDuration":0,"travelDistance":54.401,"travelDuration":37.6}]}]}],"statusCode":200,"statusDescription":"OK","traceId":"a1533c0007da4db48784576bddcdef7b|DU00002759|0.0.0.0|DU00000484"}


 //https://dev.virtualearth.net/REST/v1/Routes/DistanceMatrix?key=AvoMg355hzmFo7_Z3oXH0rlIMbBG2GQPM9kJVOpxMvpa2UaiVxm61yKNzxKJc6ks&origins=47.28534,7.70362&destinations=47.4811,8.21193&travelMode=driving
        //TODO: Parse the response to an integer (don't forget rounding)
        return 2;
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
        // APIpart1+country+APIpart2+ZIP+APIpart3+Street+Number+APIpart4
        String APIcall = APIpart1+UserCountry+APIpart2+UserZIP+APIpart3+StreetAndStreetnum+APIpart4;


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(APIcall))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        String outputAPI = response.body();
        // outputAPI = outputAPI.replaceAll("\"", "\\\"");
        String long_latitude = outputAPI.substring(outputAPI.indexOf("geocodePoints")+14,
                outputAPI.indexOf("calculationMethod")-2);
        String latitude = long_latitude.substring(long_latitude.indexOf("coordinates")+14);
        latitude = latitude.substring(0,
                latitude.indexOf(","));
        String longitude = long_latitude.substring(long_latitude.indexOf("coordinates")+14);
        longitude = longitude.substring(longitude.indexOf(",")+1, longitude.indexOf("]"));
        //TODO 1) use the address variables to form the URL which is send as GET to the API 2)use the longitude and latitude returned to form the next GET request which should return the distance
        return new String[]{latitude, longitude};
    }

    // BingMapsAPI Key: AvoMg355hzmFo7_Z3oXH0rlIMbBG2GQPM9kJVOpxMvpa2UaiVxm61yKNzxKJc6ks
    // DistanceAPI: https://dev.virtualearth.net/REST/v1/Routes/DistanceMatrix?origins=47.6044,-122.3345;47.6731,-122.1185;47.6149,-122.1936&destinations=45.5347,-122.6231;47.4747,-122.2057&travelMode=driving&key={BingMapsKey}
    // API for entering ZIP code returning ZIP Code: http://dev.virtualearth.net/REST/v1/Locations?countryRegion=Switzerland&postalCode=4528&addressLine=Luterbachstrasse 29&key=AvoMg355hzmFo7_Z3oXH0rlIMbBG2GQPM9kJVOpxMvpa2UaiVxm61yKNzxKJc6ks
    public static boolean validateAddress(Address deliveryAddress) {
        boolean isAddressValid = false;

        //TODO: call API and validate if address is correct / IS THIS IN THE CORRECT PLACE HERE
        return isAddressValid;
    }
}
