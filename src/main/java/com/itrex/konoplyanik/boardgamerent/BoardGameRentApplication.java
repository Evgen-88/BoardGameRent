package com.itrex.konoplyanik.boardgamerent;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.itrex.konoplyanik.boardgamerent.config.ApplicationContextConfiguration;
import com.itrex.konoplyanik.boardgamerent.service.FlywayService;

public class BoardGameRentApplication {
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);
		FlywayService flywayService = applicationContext.getBean(FlywayService.class);
		flywayService.migrate();
		
		flywayService.clean();
	}
	
}
