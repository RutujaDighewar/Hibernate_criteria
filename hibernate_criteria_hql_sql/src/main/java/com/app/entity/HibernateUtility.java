	package com.app.entity;

	import org.hibernate.SessionFactory;
	import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
	import org.hibernate.cfg.Configuration;
	import org.hibernate.service.ServiceRegistry;

	public class HibernateUtility {
		
		public static SessionFactory getSession() {
			Configuration cfg = new Configuration();
			cfg.configure();

			StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
			registryBuilder.applySettings(cfg.getProperties());
			ServiceRegistry serviceRegistry = registryBuilder.build();

			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceRegistry);
			return sessionFactory;
		}

	}


