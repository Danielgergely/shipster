package ch.shipster;

import ch.shipster.data.domain.Address;
import ch.shipster.data.domain.User;
import ch.shipster.security.ShipsterUserRole;
import ch.shipster.security.authentication.ShipsterUserDetails;
import ch.shipster.security.authentication.ShipsterUserDetailsService;
import ch.shipster.service.DistanceCalculator;
import ch.shipster.service.ShippingCostCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.net.MalformedURLException;

public class CalculationTest {

    @Autowired
    static
    ShippingCostCalculator shippingCostCalculator;

//Testclass, to test Distance calculations run this main class
    public static void main(String[] args) throws IOException, InterruptedException, JSONException {

        //adresse definieren
        //Address TestUserAdress = new Address("Lehnfeldstrasse", "6", "Oensingen", "4702", "Switzerland");
        //boolean Output = DistanceCalculator.validateAddress(TestUserAdress);
        //System.out.println(Output);


        /*User user = new User("dani_01", "Daniel", "Gergely", "daniel.gergely@students.fhnw.ch", "password", 999L, "male");
        user.setRoles(ShipsterUserRole.ADMIN);
        System.out.println(user.getRoles());
        ShipsterUserDetails shipsterUserDetails = new ShipsterUserDetails(user);
        System.out.println(shipsterUserDetails.getAuthorities());
        System.out.println(ShipsterUserRole.ADMIN.name());*/

        System.out.println(test());

    }

    public static Float test () throws IOException, InterruptedException {
        return shippingCostCalculator.costCalculation(1);
    }

}

