package com.journaldev.mongodb.listener;

import java.net.UnknownHostException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mongodb.MongoClient;

@WebListener
public class MongoDBContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		MongoClient mongo = (MongoClient) sce.getServletContext()
							.getAttribute("MONGO_CLIENT");
		mongo.close();
		System.out.println("MongoDBClient closed successfully");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			ServletContext ctx = sce.getServletContext();
			MongoClient mongo = new MongoClient(
					// ctx.getInitParameter("MONGODB_HOST"), 
					System.getenv("DB_PORT_27017_TCP_ADDR"),
					Integer.parseInt(ctx.getInitParameter("MONGODB_PORT")));
			System.out.println("DB_PORT_27017_TCP_ADDR = " + System.getenv("DB_PORT_27017_TCP_ADDR"));
			System.out.println("MongoDBClient initialized successfully");
			sce.getServletContext().setAttribute("MONGO_CLIENT", mongo);
		} catch (UnknownHostException e) {
			throw new RuntimeException("MongoDBClient init failed");
		}
	}

}
