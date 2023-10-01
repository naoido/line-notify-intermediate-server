package com.naoido.linenotify;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Logger;

public class LineNotifySender {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private static final String BASE_URL = "https://notify-api.line.me/api";
    private final Properties tokenProperties = new Properties();
    private final HashMap<String, String> tokens = new HashMap<>();

    public LineNotifySender() {
        try {
            this.tokenProperties.load(this.getClass().getResourceAsStream("/token.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidToken(String token) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(BASE_URL + "/status").openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.connect();

            return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void addToken(String key) {
        String token = this.tokenProperties.getProperty(key);
        if (token == null) {
            throw new IllegalArgumentException();
        }
        logger.info("Check if " + key + " token is valid...");
        if (!isValidToken(token)) {
            throw new RuntimeException("this token is invalid.");
        }

        logger.info("This token is valid!");
        this.tokens.put(key, token);
    }

    private String getToken(String key) {
        if (this.tokens.containsKey(key)) {
            return this.tokens.get(key);
        }

        try {
            addToken(key);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return getToken(key);
    }

    public void execute(String key, String message) throws IOException {
        String token = getToken(key);

        HttpURLConnection connection = (HttpURLConnection) new URL(BASE_URL + "/notify").openConnection();
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", "Bearer " + token);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        String param = "message=" + message;
        OutputStream os = connection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
        writer.write(param);
        writer.flush();
        writer.close();

        os.close();
        connection.connect();

        connection.getInputStream().close();
        logger.info("Sent a message!");

        connection.disconnect();
    }
}
