package ch.shipster.config;

// Daniel
// https://stackoverflow.com/questions/41827388/spring-security-keeps-redirecting-me-to-login-page

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ch.shipster.security.config.EnableTokenSecurity;
import ch.shipster.security.service.TokenService;
import ch.shipster.security.web.CSRFRequestMatcher;
import ch.shipster.security.web.TokenAuthenticationFilter;
import ch.shipster.security.web.TokenLoginFilter;
import ch.shipster.security.web.TokenLogoutHandler;
import ch.shipster.service.ShipsterUserDetailsService;

@EnableWebSecurity
@EnableTokenSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    ShipsterUserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenService tokenService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER).and()
                .csrf()
                .requireCsrfProtectionMatcher(new CSRFRequestMatcher())
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .authorizeRequests()
                .antMatchers("/", "/team", "/about", "/assets/**", "/register/**", "/login/**", "/api/**").permitAll()
                .antMatchers(HttpMethod.GET, "/logout").permitAll()
                .anyRequest().authenticated().and()
                .addFilter(new TokenLoginFilter(authenticationManagerBean(), tokenService))
                .addFilter(new TokenAuthenticationFilter(authenticationManagerBean(), tokenService))
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .addLogoutHandler(new TokenLogoutHandler(tokenService));
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
