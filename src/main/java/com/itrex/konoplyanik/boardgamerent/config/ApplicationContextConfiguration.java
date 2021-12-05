package com.itrex.konoplyanik.boardgamerent.config;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class ApplicationContextConfiguration {
	@Value("${logging.profile.info}")
	private String loggingProfileInfo;
	@Value("${logging.profile.debug}")
	private String loggingProfileDebug;

	@Bean
	@Profile("info")
	public void configureLoggingInfo() {
		PropertyConfigurator.configure(loggingProfileInfo);
	}

	@Bean
	@Profile("debug")
	public void configureLoggingDebug() {
		PropertyConfigurator.configure(loggingProfileDebug);
	}
	
	@Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
