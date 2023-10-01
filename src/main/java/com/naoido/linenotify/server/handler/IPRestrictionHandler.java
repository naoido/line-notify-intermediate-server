package com.naoido.linenotify.server.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.HandlerWrapper;

import java.io.IOException;
import java.util.Set;

public class IPRestrictionHandler extends HandlerWrapper {
    private final Set<String> ALLOWED_IP;
    
    public IPRestrictionHandler(Set<String> allowIp) {
        this.ALLOWED_IP = allowIp;
    }
    
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String clientIp = request.getRemoteAddr();

        if (this.ALLOWED_IP.contains(clientIp)) {
            _handler.handle(target, baseRequest, request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            baseRequest.setHandled(true);
        }
    }
}
