package ch.shipster;

import ch.shipster.service.ShipsterLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShipsterApplication {

    public static void main(String[] args){
        SpringApplication.run(ShipsterApplication.class, args);
       //ShipsterLogger programInstance = new ShipsterLogger();
       // programInstance.doStuff();
    }
}
