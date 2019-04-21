package io.lybo.rpc.spring;

import io.lybo.rpc.spring.resgistery.RegistryParser;
import io.lybo.rpc.spring.service.ServiceParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class NettyRpcNameSpaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("registry",new RegistryParser());
        registerBeanDefinitionParser("service",new ServiceParser());
        registerBeanDefinitionParser("reference",new ReferenceParser());
    }
}
