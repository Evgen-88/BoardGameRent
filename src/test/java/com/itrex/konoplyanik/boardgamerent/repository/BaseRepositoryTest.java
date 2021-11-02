package com.itrex.konoplyanik.boardgamerent.repository;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.itrex.konoplyanik.boardgamerent.config.ApplicationContextConfiguration;
import com.itrex.konoplyanik.boardgamerent.service.FlywayService;

public abstract class BaseRepositoryTest {
	
	private final ApplicationContext applicationContext;
	private final FlywayService flywayService;
	
	public BaseRepositoryTest() {
		applicationContext = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);
		flywayService = applicationContext.getBean(FlywayService.class);
	}
	
	@Before
    public void initDB() {
        flywayService.migrate();
    }

    @After
    public void cleanDB() {
        flywayService.clean();
    }

    @Autowired
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
