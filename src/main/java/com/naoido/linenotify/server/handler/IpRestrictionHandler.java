package com.naoido.linenotify.server.handler;

import com.naoido.linenotify.server.model.AllowIps;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.HandlerWrapper;

import java.io.IOException;

public class IpRestrictionHandler extends HandlerWrapper {
    private final AllowIps ALLOWED_IPS;

    public IpRestrictionHandler(AllowIps allowIps) {
        this.ALLOWED_IPS = allowIps;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String clientIp = request.getRemoteAddr();

        if (this.ALLOWED_IPS.isAllowIp(clientIp)) {
            _handler.handle(target, baseRequest, request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            baseRequest.setHandled(true);
        }
    }
}
