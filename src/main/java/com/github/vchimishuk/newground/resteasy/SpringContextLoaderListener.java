package com.github.vchimishuk.newground.resteasy;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.jboss.resteasy.plugins.spring.SpringContextLoaderSupport;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

/*
 * Based on SpringContextLoaderListener implementation from official Resteasy Spring plugin.
 *
 * See: https://github.com/resteasy/Resteasy/blob/master/jaxrs/resteasy-spring/src/main/java/org/jboss/resteasy/plugins/spring/SpringContextLoaderListener.java
 */
public class SpringContextLoaderListener extends ContextLoaderListener {
    private SpringContextLoaderSupport springContextLoaderSupport = new SpringContextLoaderSupport();

    public SpringContextLoaderListener() {

    }

    public SpringContextLoaderListener(WebApplicationContext context) {
        super(context);
    }

    public void contextInitialized(ServletContextEvent event) {
        boolean scanProviders = false;
        boolean scanResources = false;
        String sProviders = event.getServletContext().getInitParameter("resteasy.scan.providers");
        if(sProviders != null) {
            scanProviders = Boolean.valueOf(sProviders.trim());
        }

        String scanAll = event.getServletContext().getInitParameter("resteasy.scan");
        if(scanAll != null) {
            boolean sResources = Boolean.valueOf(scanAll.trim());
            scanProviders = sResources || scanProviders;
            scanResources = sResources || scanResources;
        }

        String sResources1 = event.getServletContext().getInitParameter("resteasy.scan.resources");
        if(sResources1 != null) {
            scanResources = Boolean.valueOf(sResources1.trim());
        }

        if(!scanProviders && !scanResources) {
            super.contextInitialized(event);
        } else {
            throw new RuntimeException("You cannot use resteasy.scan, resteasy.scan.resources, "
                    + "or resteasy.scan.providers with the SpringContextLoaderLister as this may "
                    + "cause serious deployment errors in your application");
        }
    }

    protected void customizeContext(ServletContext servletContext, ConfigurableWebApplicationContext configurableWebApplicationContext) {
        super.customizeContext(servletContext, configurableWebApplicationContext);
        springContextLoaderSupport.customizeContext(servletContext, configurableWebApplicationContext);
    }
}
