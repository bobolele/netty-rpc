package io.lybo.rpc.boot;

import io.lybo.rpc.model.MessageRequest;
import io.lybo.rpc.netty.send.MessageSendExecutor;

import java.util.UUID;

/**
 * 客户端启动器demo
 */
public class ClientStarter {
    public static void main(String[] args) {
        MessageSendExecutor executor = MessageSendExecutor.getInstance();

        MessageRequest request = new MessageRequest();
        request.setMessageId(UUID.randomUUID().toString());

        String execute = (String) executor.execute(request);
        System.out.println("result : " + execute);


//        executor.stop();
    }
}
