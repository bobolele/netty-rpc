package io.lybo.rpc.spring.resgistery;

import io.lybo.rpc.netty.receiver.MessageRecvExecutor;
import org.springframework.beans.factory.InitializingBean;

public class NettyRpcRegistry implements InitializingBean {
    private String ip;
    private int port;

    public void start() {
        MessageRecvExecutor ref = MessageRecvExecutor.getInstance();
        ref.start(ip,port);

    }

    public void destory() {
        MessageRecvExecutor.getInstance().stop();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
       start();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
