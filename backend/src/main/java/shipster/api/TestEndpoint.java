package shipster.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/test")
public class TestEndpoint {

    @GetMapping(path = "/hello")
    public String getTestHello(@RequestParam(value = "name", defaultValue = "YOU awesome person!!") String name) {
        return String.format("Hello %s!", name);
    }
}
