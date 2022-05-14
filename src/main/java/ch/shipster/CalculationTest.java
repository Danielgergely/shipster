package ch.shipster;


import ch.shipster.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.configurationprocessor.json.JSONException;


import javax.annotation.PostConstruct;
import java.io.IOException;


@SpringBootApplication
public class CalculationTest {
private static String [] args;
@Autowired
static
CheckoutService checkoutService;

//Testclass, to test Distance calculations run this main class
    public static void main(String[] args) throws IOException, InterruptedException, JSONException {
        CalculationTest.args=args;
        SpringApplication.run(CalculationTest.class, args);

        System.out.println(checkoutService.calculateTotalOrderPriceWithShipping(1L, 1L));

        System.out.println("gugus");


    }
    @PostConstruct
    public void init() throws IOException, InterruptedException {
        System.out.println(checkoutService.calculateTotalOrderPriceWithShipping(1L, 1L));
        System.out.println("gugus");
    }

}

