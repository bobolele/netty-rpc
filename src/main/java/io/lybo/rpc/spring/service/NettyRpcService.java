package io.lybo.rpc.spring.service;

import io.lybo.rpc.netty.receiver.MessageRecvExecutor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class NettyRpcService implements ApplicationContextAware,ApplicationListener {
    private String interfaceName;
    private String ref;


    private ApplicationContext applicationContext;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        MessageRecvExecutor.getInstance().regist(interfaceName,applicationContext.getBean(ref));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
