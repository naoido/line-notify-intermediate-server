package com.naoido.linenotify.server.model;

import com.fasterxml.jackson.annotation.*;

import java.util.regex.Pattern;

public class AllowIp {
    @JsonIgnore
    private final String[] ip;

    @JsonCreator
    public AllowIp(String ip) {
        if (!isIpv4Address(ip)) throw new IllegalArgumentException();
        this.ip = ip.split("\\.");
    }

    public static AllowIp of(String value) {
        return new AllowIp(value);
    }

    public boolean isAllowIp(String ip) {
        if (!isIpv4Address(ip)) throw new IllegalArgumentException();

        String[] octets = ip.split("\\.");

        for (int i = 0; i < 4; i++) {
            if (this.ip[i].equals("*")) continue;
            if (!octets[i].equals(this.ip[i])) return false;
        }
        return true;
    }

    private boolean isIpv4Address(String ip) {
        return Pattern.compile("(([1-9]?[0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]|\\*)\\.){3}([1-9]?[0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]|\\*)").matcher(ip).find();
    }

    @JsonValue
    @Override
    public String toString() {
        return this.ip[0] + "." + this.ip[1] + "." + this.ip[2] + "." + this.ip[3];
    }
}
