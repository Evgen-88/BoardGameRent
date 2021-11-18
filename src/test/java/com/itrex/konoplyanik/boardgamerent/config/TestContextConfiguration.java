package com.itrex.konoplyanik.boardgamerent.config;

import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

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
	@Value("${logging.profile.info}")
	private String loggingProfileInfo;
	@Value("${logging.profile.debug}")
	private String loggingProfileDebug;
	
	@Bean
	public SessionFactory sessionFactory() {
		return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
	}
	
	@Bean(initMethod = "migrate")
	public Flyway flyway() {
		return Flyway.configure()
				.dataSource(url, user, password)
				.locations(migrationLocation)
				.load();
	}
	
}
