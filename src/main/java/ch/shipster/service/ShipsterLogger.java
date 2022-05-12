package ch.shipster.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



// Jonas
// https://www.youtube.com/watch?v=lGrcZsw-hKQ - How to do logging in Spring Boot - Brain Bytes
//How to write logs to a file with Spring Boot
// https://www.tutorialworks.com/spring-boot-log-to-file/#:~:text=To%20make%20Spring%20Boot%20write%20its%20log%20to,set%20the%20path%20and%20filename.&text=With%20this%20configuration%2C%20Spring%20Boot,at%20the%20path%20you%20specify.

@Service
public class ShipsterLogger {

    public static Logger logger = LoggerFactory.getLogger(ShipsterLogger.class);


}
