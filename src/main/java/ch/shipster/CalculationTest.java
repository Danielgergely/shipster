package ch.shipster;

import ch.shipster.data.domain.Address;
import ch.shipster.service.DistanceCalculator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;

public class CalculationTest {


//Testclass, to test Distance calculations run this main class
    public static void main(String[] args) throws IOException, InterruptedException, JSONException {

        //adresse definieren
        Address TestUserAdress = new Address("Lehnfeldstrasse", "6", "Oensingen", "4702", "Oensingen");
        String Output = DistanceCalculator.calculateDistance(TestUserAdress);
        System.out.println(Output);
        System.out.println("Test");
    }

}

