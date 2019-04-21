package io.lybo.rpc.netty.send.map;

import io.lybo.rpc.netty.send.MessageCallback;

import java.util.concurrent.ConcurrentHashMap;

public class CallBackMap extends ConcurrentHashMap<String, MessageCallback> {

    private static final CallBackMap instance = new CallBackMap();

    private CallBackMap() {
    }

    public static CallBackMap getInstance() {
        return instance;
    }
}
