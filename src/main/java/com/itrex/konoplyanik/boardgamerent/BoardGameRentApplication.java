package com.itrex.konoplyanik.boardgamerent;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.AbstractEnvironment;

import com.itrex.konoplyanik.boardgamerent.config.ApplicationContextConfiguration;
import com.itrex.konoplyanik.boardgamerent.repository.RoleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BoardGameRentApplication {
	
	public static void main(String[] args) {
		System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "info");
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);
		
		RoleRepository roleRepository = applicationContext.getBean(RoleRepository.class);
		System.out.print(roleRepository.delete(2L));
		
		log.info("info");
		log.debug("debug");
	}

}
