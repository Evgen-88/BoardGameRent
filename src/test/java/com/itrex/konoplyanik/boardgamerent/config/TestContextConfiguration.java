package com.itrex.konoplyanik.boardgamerent.config;

import static com.itrex.konoplyanik.boardgamerent.properties.TestProperties.H2_PASSWORD;
import static com.itrex.konoplyanik.boardgamerent.properties.TestProperties.H2_URL;
import static com.itrex.konoplyanik.boardgamerent.properties.TestProperties.H2_USER;
import static com.itrex.konoplyanik.boardgamerent.properties.TestProperties.MIGRATION_LOCATION;

import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("com.itrex.konoplyanik.boardgamerent.repository")
public class TestContextConfiguration {

	@Bean
	public SessionFactory sessionFactory() {
		return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
	}
	
	@Bean
	public Flyway flyway() {
		Flyway flyway = Flyway.configure().dataSource(H2_URL, H2_USER, H2_PASSWORD).locations(MIGRATION_LOCATION)
				.load();
		return flyway;
	}
	
}
