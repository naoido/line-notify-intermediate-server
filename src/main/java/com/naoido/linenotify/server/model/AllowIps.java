package com.naoido.linenotify.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Set;

public class AllowIps {
    @JsonValue
    private final Set<AllowIp> allowIps;

    @JsonCreator
    public AllowIps(Set<AllowIp> allowIps) {
        this.allowIps = allowIps;
    }

    public boolean isAllowIp(String ip) {
        for (AllowIp allowIp: this.allowIps) {
            if (allowIp.isAllowIp(ip)) return true;
        }
        return false;
    }
}
