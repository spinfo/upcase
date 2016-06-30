package de.uni_koeln.spinfo.upcase.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer /*implements WebApplicationInitializer*/ {

//	@Override
//	public void onStartup(ServletContext context) {
//		// Create the 'root' Spring application context
//		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
//		rootContext.register(AppConfig.class);
//
//		// Manage the lifecycle of the root application context
//		context.addListener(new ContextLoaderListener(rootContext));
//
//		// Register and map the dispatcher servlet
//		ServletRegistration.Dynamic dispatcher = context.addServlet("dispatcher", new DispatcherServlet(rootContext));
//		dispatcher.setLoadOnStartup(1);
//		dispatcher.addMapping("/");
//	}

}
