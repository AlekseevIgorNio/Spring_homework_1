package org.example.app.config.web;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration;
import org.apache.log4j.Logger;
import org.h2.server.web.JakartaWebServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;

public class WebInitializer implements WebApplicationInitializer {

    Logger logger = Logger.getLogger(WebInitializer.class);

    @Override
    public void onStartup(jakarta.servlet.ServletContext servletContext) {
        logger.info("loading web config");
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.register(WebConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("my-dispatcher-servlet", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        MultipartConfigElement multipartConfig = new MultipartConfigElement(
                System.getProperty("catalina.home")+ File.separator + "my_data", 50000000, 50000000, 0);
        dispatcher.setMultipartConfig(multipartConfig);

        ServletRegistration.Dynamic servletH2 = servletContext.addServlet("h2-console", new JakartaWebServlet());
        servletH2.setLoadOnStartup(2);
        servletH2.addMapping("/console/*");

        logger.info("dispatcher");
    }
}