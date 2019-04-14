package io.lybo.rpc.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class NettyRpcNameSpaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("reference",new ReferenceParser());
    }
}
