package io.lybo.rpc.boot;

import io.lybo.rpc.netty.receiver.MessageRecvExecutor;

public class ServerStarter {
    public static void main(String[] args) {
        MessageRecvExecutor executor = new MessageRecvExecutor();
        executor.start();
    }
}
