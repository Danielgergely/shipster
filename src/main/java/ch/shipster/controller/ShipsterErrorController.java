package ch.shipster.controller;

import ch.shipster.service.ShipsterLogger;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

// Daniel
// https://www.baeldung.com/spring-boot-custom-error-page
@Controller
public class ShipsterErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                ShipsterLogger.logger.error("Error/404 occurred");
                return "error/404";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                ShipsterLogger.logger.error("Error/500 occurred");
                return "error/500";
            }
            else if(statusCode == HttpStatus.FORBIDDEN.value()) {
                ShipsterLogger.logger.error("Error/403 occurred");
                return "error/403";
            }
        }
        return "error/404";
    }
}
