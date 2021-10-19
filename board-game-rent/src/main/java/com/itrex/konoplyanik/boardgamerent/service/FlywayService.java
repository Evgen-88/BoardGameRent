package com.itrex.konoplyanik.boardgamerent.service;

import static com.itrex.konoplyanik.boardgamerent.properties.Properties.H2_URL;
import static com.itrex.konoplyanik.boardgamerent.properties.Properties.H2_USER;
import static com.itrex.konoplyanik.boardgamerent.properties.Properties.H2_PASSWORD;
import static com.itrex.konoplyanik.boardgamerent.properties.Properties.MIGRATION_LOCATION;

import org.flywaydb.core.Flyway;

public class FlywayService {
	
	private Flyway flyway;
	
	public FlywayService() {
		init();
	}
	
	public void migrate() {
		flyway.migrate();
	}
	
	public void clean() {
		flyway.clean();
	}
	
	private void init() {
		flyway = Flyway.configure()
				.dataSource(H2_URL, H2_USER, H2_PASSWORD)
				.locations(MIGRATION_LOCATION)
				.load();
	}
	
}
