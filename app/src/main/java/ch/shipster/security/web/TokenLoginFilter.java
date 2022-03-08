package ch.shipster.security.web;

// Daniel
// based on the digipr-arcm project from the Internet technology class: https://github.com/Danielgergely/digipr-acrm

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ch.shipster.security.config.TokenSecurityProperties;
import ch.shipster.security.model.TokenUser;
import ch.shipster.security.service.TokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
    private final TokenService tokenService;
    private TokenUser user = null;

    public TokenLoginFilter(AuthenticationManager authenticationManager, TokenService tokenService) {
        super.setAuthenticationManager(authenticationManager);
        this.tokenService = tokenService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) {

        try {
            this.user = new ObjectMapper().readValue(request.getInputStream(), TokenUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword())
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        Date date = null;
        Cookie cookie = null;

        if(Boolean.parseBoolean(this.user.getRemember())) {
            date = new Date(System.currentTimeMillis() + TokenSecurityProperties.REMEMBER_EXPIRATION_TIME);
        }else{
            date = new Date(System.currentTimeMillis() + TokenSecurityProperties.SESSION_EXPIRATION_TIME);
        }
        String cookieToken = this.tokenService.issueToken(this.user.getEmail(), TokenSecurityProperties.COOKIE_TYPE, date);
        cookie = new Cookie(TokenSecurityProperties.COOKIE_NAME, cookieToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        if(Boolean.parseBoolean(this.user.getRemember())) {
            cookie.setMaxAge(Math.toIntExact(TokenSecurityProperties.REMEMBER_EXPIRATION_TIME /1000));
        }
        response.addCookie(cookie);

        date = new Date(System.currentTimeMillis() + TokenSecurityProperties.BEARER_EXPIRATION_TIME);
        String bearerToken = this.tokenService.issueToken(this.user.getEmail(), TokenSecurityProperties.BEARER_TYPE, date);
        response.addHeader(TokenSecurityProperties.HEADER_NAME, TokenSecurityProperties.BEARER_TOKEN_PREFIX + bearerToken);
    }
}
