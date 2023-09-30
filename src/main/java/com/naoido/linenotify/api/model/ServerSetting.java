package com.naoido.linenotify.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerSetting {
    @JsonProperty("port")
    private int port;
    @JsonProperty("allow-ip")
    private String allowIp;

    public ServerSetting() {}

    public int getPort() {
        return this.port;
    }

    public String getAllowIp() {
        return this.allowIp;
    }
}
