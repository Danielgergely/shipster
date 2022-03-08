package ch.shipster.security.web;

// Daniel
// based on the digipr-arcm project from the Internet technology class: https://github.com/Danielgergely/digipr-acrm

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;

public class CSRFRequestMatcher implements RequestMatcher {

    private final HashSet<String> allowedMethods = new HashSet<>(Arrays.asList("GET", "HEAD", "TRACE", "OPTIONS"));

    @Override
    public boolean matches(HttpServletRequest request) {
        return !this.allowedMethods.contains(request.getMethod()) && request.getCookies() != null;
    }
}
