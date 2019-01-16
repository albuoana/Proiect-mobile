package utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = "repositories")
@ComponentScan(basePackages = "model")
@PropertySource("persistence.properties")
@EnableTransactionManagement
public class PersistenceConfig {
    @Autowired
    Environment environment;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        Map<String, String> propMap = new HashMap<>();

        propMap.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        propMap.put("hibernate.hbm2ddl.auto", "update");

        entityManager.setDataSource(this.getDataSource());
        entityManager.setJpaPropertyMap(propMap);
        entityManager.setPackagesToScan("model");
        entityManager.setJpaVendorAdapter(adapter);

        return entityManager;
    }

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
        ds.setUrl(environment.getProperty("jdbc.url"));
        ds.setUsername(environment.getProperty("jdbc.user"));
        ds.setPassword(environment.getProperty("jdbc.pass"));
        return ds;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}