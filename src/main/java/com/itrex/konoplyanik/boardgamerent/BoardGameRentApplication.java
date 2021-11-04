package com.itrex.konoplyanik.boardgamerent;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.flywaydb.core.Flyway;

import com.itrex.konoplyanik.boardgamerent.config.ApplicationContextConfiguration;

public class BoardGameRentApplication {
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);
		Flyway flyway = applicationContext.getBean(Flyway.class);
		flyway.migrate();
		
		flyway.clean();
	}
	
}
