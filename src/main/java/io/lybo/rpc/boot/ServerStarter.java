package io.lybo.rpc.boot;

import io.lybo.rpc.netty.receiver.MessageRecvExecutor;

public class ServerStarter {
    public static void main(String[] args) {
        MessageRecvExecutor executor = MessageRecvExecutor.getInstance();
        executor.start("127.0.0.1",16789);
    }
}
