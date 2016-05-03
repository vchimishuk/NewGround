package com.github.vchimishuk.newground;

import com.github.vchimishuk.newground.resteasy.SpringContextLoaderListener;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class Main {
    public static void main(String[] args) throws Exception {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.scan("com.github.vchimishuk.newground");

        ServletContextHandler handler = new ServletContextHandler();
        handler.addEventListener(new ResteasyBootstrap());
        handler.addEventListener(new SpringContextLoaderListener(context));
        handler.addServlet(new ServletHolder(HttpServletDispatcher.class), "/*");

        Server server = new Server(8888);
        server.setHandler(handler);

        server.start();
    }
}
