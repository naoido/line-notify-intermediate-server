package com.naoido.linenotify.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public class ServerSetting {
    @JsonProperty("port")
    private int port;
    @JsonProperty("allow-ips")
    private Set<String> allowIps;

    public ServerSetting() {}

    public int getPort() {
        return this.port;
    }

    public Set<String> getAllowIps() {
        return this.allowIps;
    }

    public static ServerSetting createDefault() {
        ServerSetting serverSetting = new ServerSetting();

        // Default Settings
        serverSetting.port = 9800;
        serverSetting.allowIps = new HashSet<>();

        return serverSetting;
    }
}
