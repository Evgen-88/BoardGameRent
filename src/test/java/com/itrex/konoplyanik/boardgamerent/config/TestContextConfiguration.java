package com.itrex.konoplyanik.boardgamerent.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@Configuration
@PropertySource("classpath:/application.properties")
@ComponentScan("com.itrex.konoplyanik.boardgamerent")
public class TestContextConfiguration {

	@Value("${database.url}")
	private String url;
	@Value("${database.user}")
	private String user;
	@Value("${database.password}")
	private String password;
	@Value("${database.migration.location}")
	private String migrationLocation;
	@Value("${hibernate.driver}")
	private String hibernateDriver;
	@Value("${hibernate.dialect}")
	private String hibernateDialect;
	@Value("${hibernate.show_sql}")
	private String hibernateShowSql;
	@Value("${hibernate.format_sql}")
	private String hibernateFormatSql;
	@Value("${entity.path}")
	private String entityPath;

	@Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(entityPath);
        sessionFactory.setHibernateProperties(properties());
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
    	DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(hibernateDriver);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public Properties properties() {
        Properties properties = new Properties();
        properties.setProperty("connection.url", url);
        properties.setProperty("connection.driver_class", hibernateDriver);
        properties.setProperty("connection.username", user);
        properties.setProperty("connection.password", password);
        properties.setProperty("dialect", hibernateDialect);
        properties.setProperty("show_sql", hibernateShowSql);
        properties.setProperty("format_sql", hibernateFormatSql);
        return properties;
    }

	@Bean(initMethod = "migrate")
	public Flyway flyway() {
		return Flyway.configure().dataSource(url, user, password).locations(migrationLocation).load();
	}
	
}
