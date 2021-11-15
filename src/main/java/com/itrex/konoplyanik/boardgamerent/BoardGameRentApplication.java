package com.itrex.konoplyanik.boardgamerent;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.itrex.konoplyanik.boardgamerent.config.ApplicationContextConfiguration;
import com.itrex.konoplyanik.boardgamerent.repository.RoleRepository;

public class BoardGameRentApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);
		RoleRepository roleRepository = applicationContext.getBean(RoleRepository.class);
		System.out.print(roleRepository.findById(1L));
	}

}
