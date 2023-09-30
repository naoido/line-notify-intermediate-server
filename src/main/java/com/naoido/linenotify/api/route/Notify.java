package com.naoido.linenotify.api.route;

import com.naoido.linenotify.Main;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class Notify extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String tokenKey = req.getParameter("key");
        String message = req.getParameter("message");

        if (tokenKey == null || message == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            Main.notifySender.execute(tokenKey, message);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
