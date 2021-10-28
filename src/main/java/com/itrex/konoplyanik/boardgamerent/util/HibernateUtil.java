package com.itrex.konoplyanik.boardgamerent.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static SessionFactory sessionFactory;
	
	public static SessionFactory getSessionFactory() {
		if(sessionFactory == null) {
			try {
				sessionFactory = new Configuration().configure().buildSessionFactory();
			} catch(HibernateException ex) {
				System.err.println("Failed to create sessionFactory object." + ex);
			}
		}
		return sessionFactory;
	}
	
}