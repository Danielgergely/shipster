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

    Address warehouseAddress = new Address("Bahnhofstrasse", "6", "Windisch", "5210", "Switzerland");



    public static String calculateDistance(Address deliveryAddress) throws IOException, InterruptedException, JSONException {
        // TODO: call google API and calculate distance between warehouse and to address
        String distance = "1";

        String UserStreet = deliveryAddress.getStreet();
        String UserAddressNumber = deliveryAddress.getNumber();
        String UserAddress = deliveryAddress.getZip();
        String UserCity = deliveryAddress.getCity();
        String UserCountry = deliveryAddress.getCountry();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://dev.virtualearth.net/REST/v1/Locations?countryRegion=Switzerland&postalCode=9999&addressLine=Luterbachstrasse%2029&key=AvoMg355hzmFo7_Z3oXH0rlIMbBG2GQPM9kJVOpxMvpa2UaiVxm61yKNzxKJc6ks"))
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

         return outputAPI;
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
