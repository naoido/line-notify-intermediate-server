package com.naoido.linenotify.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class ServerSetting {
    @JsonProperty("port")
    private int port;
    @JsonProperty("allow-ips")
    private AllowIps allowIps;

    public ServerSetting() {}

    public int getPort() {
        return this.port;
    }

    public AllowIps getAllowIps() {
        return this.allowIps;
    }

    public static ServerSetting createDefault() {
        ServerSetting serverSetting = new ServerSetting();

        // Default Settings
        serverSetting.port = 9800;
        serverSetting.allowIps = new AllowIps(Set.of(AllowIp.of("*.*.*.*"), AllowIp.of("1.*.*.*")));

        return serverSetting;
    }
}
