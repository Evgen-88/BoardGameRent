package com.itrex.konoplyanik.boardgamerent.repository;

import org.h2.jdbcx.JdbcConnectionPool;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;

import static com.itrex.konoplyanik.boardgamerent.properties.Properties.*;

import com.itrex.konoplyanik.boardgamerent.service.FlywayService;
import com.itrex.konoplyanik.boardgamerent.util.HibernateUtil;

public abstract class BaseRepositoryTest {
	
	private final FlywayService flywayService;
	private final JdbcConnectionPool connectionPool;
	private final SessionFactory sessionfactory;
	
	public BaseRepositoryTest() {
		flywayService = new FlywayService();
		connectionPool = JdbcConnectionPool.create(H2_URL, H2_USER, H2_PASSWORD);
		sessionfactory = HibernateUtil.getSessionFactory();
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
    
    public SessionFactory getSessionFactory() {
        return sessionfactory;
    }
	
}
