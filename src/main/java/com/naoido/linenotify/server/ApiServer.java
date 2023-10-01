package com.naoido.linenotify.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.naoido.linenotify.server.handler.IPRestrictionHandler;
import com.naoido.linenotify.server.model.ServerSetting;
import com.naoido.linenotify.server.route.Other;
import com.naoido.linenotify.server.route.Notify;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApiServer {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final String JSON_PATH = "setting.json";

    public ApiServer() throws Exception {
        ServerSetting serverSetting = loadServerSetting();

        Server server = new Server(serverSetting.getPort());
        ServerConnector connector = new ServerConnector(server);
        connector.setHost("0.0.0.0");

        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(new ServletHolder(new Notify()), "/notify");
        servletHandler.addServletWithMapping(new ServletHolder(new Other()), "/*");

        IPRestrictionHandler ipRestrictionHandler = new IPRestrictionHandler(serverSetting.getAllowIps());
        ipRestrictionHandler.setHandler(servletHandler);

        server.addConnector(connector);
        server.setHandler(ipRestrictionHandler);
        server.start();
    }

    private ServerSetting loadServerSetting() {
        ObjectMapper mapper = new ObjectMapper();
        ServerSetting setting;
        try {
            setting = mapper.readValue(new File(JSON_PATH), ServerSetting.class);
        } catch (IOException e) {
            if (!createServerSetting()) {
                throw new RuntimeException(e);
            }
            logger.info("デフォルト設定を読み込みました。");
            return ServerSetting.createDefault();
        }
        logger.info("設定ファイルを読み込みました。");
        return setting;
    }

    private boolean createServerSetting() {
        ServerSetting serverSetting = ServerSetting.createDefault();
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        File jsonFile = new File(JSON_PATH);
        try {
            if (!jsonFile.exists()) {
                createJsonFile();
            }

            mapper.writeValue(new File(JSON_PATH), serverSetting);
        } catch (IOException e) {
            logger.log(Level.WARNING, "JSONファイルが書き込めませんでした。", e);
            return false;
        }
        logger.info("JSONファイルを新しく生成しました。");
        return true;
    }

    private boolean createJsonFile() throws IOException {
        return new File(JSON_PATH).createNewFile();
    }
}
