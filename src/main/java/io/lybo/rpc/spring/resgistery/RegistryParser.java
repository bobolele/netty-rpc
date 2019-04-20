package io.lybo.rpc.spring.resgistery;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class RegisteyParser  implements BeanDefinitionParser {
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        String id = element.getAttribute("id");
        String ip = element.getAttribute("ip");
        int port = Integer.parseInt(element.getAttribute("port"));

        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(NettyRpcRegistery.class);
        beanDefinition.setLazyInit(false);

        beanDefinition.getPropertyValues().addPropertyValue("ip", ip);
        beanDefinition.getPropertyValues().addPropertyValue("port", port);

        parserContext.getRegistry().registerBeanDefinition(id,beanDefinition);

        return beanDefinition;
    }
}
