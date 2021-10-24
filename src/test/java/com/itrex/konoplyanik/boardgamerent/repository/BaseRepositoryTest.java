package com.itrex.konoplyanik.boardgamerent.repository;

import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.After;
import org.junit.Before;

import static com.itrex.konoplyanik.boardgamerent.properties.Properties.*;

import com.itrex.konoplyanik.boardgamerent.service.FlywayService;

public abstract class BaseRepositoryTest {
	
	private final FlywayService flywayService;
	private final JdbcConnectionPool connectionPool;
	
	public BaseRepositoryTest() {
		flywayService = new FlywayService();
		connectionPool = JdbcConnectionPool.create(H2_URL, H2_USER, H2_PASSWORD);
	}
	
	@Before
    public void initDB() {
        flywayService.migrate();
    }

    @After
    public void cleanDB() {
        flywayService.clean();
    }

    public JdbcConnectionPool getConnectionPool() {
        return connectionPool;
    }
	
}
