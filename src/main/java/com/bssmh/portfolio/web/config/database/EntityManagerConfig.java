package com.bssmh.portfolio.web.config.database;

import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.MariaDBDialect;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaAuditing
@EntityScan(basePackages = {"com.bssmh.portfolio"})
@EnableJpaRepositories(basePackages = {"com.bssmh.portfolio"})
@RequiredArgsConstructor
public class EntityManagerConfig {

    private static final String[] PACKAGE_TO_SCAN = {"com.bssmh.portfolio"};
    private final DataSource dataSource;

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(AvailableSettings.HBM2DDL_AUTO, "update");
        properties.put(AvailableSettings.DEFAULT_CATALOG, "bssmh");
        properties.put(AvailableSettings.PHYSICAL_NAMING_STRATEGY, "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        properties.put(AvailableSettings.DEFAULT_BATCH_FETCH_SIZE, 500);
        properties.put(AvailableSettings.DIALECT, MariaDBDialect.class);

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan(PACKAGE_TO_SCAN);
        emf.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        emf.setJpaPropertyMap(properties);
        return emf;
    }
}
