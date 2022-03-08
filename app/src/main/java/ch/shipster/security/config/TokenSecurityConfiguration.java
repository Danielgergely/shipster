package ch.shipster.security.config;

// Daniel
// based on the digipr-arcm project from the Internet technology class: https://github.com/Danielgergely/digipr-acrm

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class TokenSecurityConfiguration {
    private final EntityManagerFactory entityManagerFactory;
    private final EntityManagerFactory entityManagerFactorySecurity;

    @Autowired
    public TokenSecurityConfiguration(DataSource dataSource, JpaVendorAdapter vendorAdapter, EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan("ch.shipster.security.model");
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setJpaProperties(additionalProperties());
        entityManagerFactoryBean.afterPropertiesSet();
        this.entityManagerFactorySecurity = entityManagerFactoryBean.getObject();
    }

    @Bean
    public EntityManager entityManagerSecurity() {
        return entityManagerFactorySecurity.createEntityManager();
    }

    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.putAll(entityManagerFactory.getProperties());
        properties.remove("hibernate.transaction.coordinator_class"); //Spring Data issue
        return properties;
    }
}
