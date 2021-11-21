package com.itrex.konoplyanik.boardgamerent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.itrex.konoplyanik.boardgamerent.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class BoardGameRentApplication implements CommandLineRunner {

	@Autowired
	ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(BoardGameRentApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		UserRepository userRepository = applicationContext.getBean(UserRepository.class);
		System.out.println(userRepository.findUsersByRole(3L));
		
		log.info("info");
		log.debug("debug");

	}

}
