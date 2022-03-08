package ch.shipster.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ch.shipster.security.service.TokenService;

import java.lang.annotation.*;

//Daniel
// based on the digipr-arcm project from the Internet technology class: https://github.com/Danielgergely/digipr-acrm

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({TokenSecurityConfiguration.class, TokenSecurityProperties.class, TokenService.class})
@Configuration
public @interface EnableTokenSecurity {
}
