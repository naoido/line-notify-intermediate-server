package com.naoido.linenotify.api;

import com.naoido.linenotify.api.model.ServerSetting;
import com.naoido.linenotify.api.route.Other;
import com.naoido.linenotify.api.route.Notify;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class ApiServer {
    public ApiServer() throws Exception {
        ServerSetting serverSetting = new ServerSetting();
        Server server = new Server(8080);
        ServerConnector connector = new ServerConnector(server);
        connector.setHost("0.0.0.0");

        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(new ServletHolder(new Notify()), "/notify");
        handler.addServletWithMapping(new ServletHolder(new Other()), "/*");

        server.addConnector(connector);
        server.setHandler(handler);
        server.start();
    }
}
