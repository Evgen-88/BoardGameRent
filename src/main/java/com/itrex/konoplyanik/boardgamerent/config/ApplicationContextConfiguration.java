package com.itrex.konoplyanik.boardgamerent.config;

import org.hibernate.Session;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.itrex.konoplyanik.boardgamerent.service.FlywayService;
import com.itrex.konoplyanik.boardgamerent.util.HibernateUtil;

@Configuration
@ComponentScan("com.itrex.konoplyanik.boardgamerent")
public class ApplicationContextConfiguration {

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Session session() {
		return HibernateUtil.getSessionFactory().openSession();
	}
	
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public FlywayService flywayService() {
		FlywayService flywayService = new FlywayService();
		flywayService.migrate();
		return flywayService;
	}
	
}
