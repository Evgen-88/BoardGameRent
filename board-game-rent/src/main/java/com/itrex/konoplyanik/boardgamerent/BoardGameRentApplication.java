package com.itrex.konoplyanik.boardgamerent;

import com.itrex.konoplyanik.boardgamerent.service.FlywayService;

public class BoardGameRentApplication {
	
	public static void main(String[] args) {
		FlywayService flywayService = new FlywayService();
		flywayService.migrate();

	}
	
}
