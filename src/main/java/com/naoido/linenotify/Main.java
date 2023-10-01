package com.naoido.linenotify;

import com.naoido.linenotify.server.ApiServer;

public class Main {
    public static final LineNotifySender notifySender = new LineNotifySender();

    public static void main(String[] args) throws Exception {
        new ApiServer();
    }
}
