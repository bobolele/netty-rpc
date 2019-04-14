package io.lybo.rpc.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class ReferenceParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext context) {
        String id = element.getAttribute("id");
        String interfaceName = element.getAttribute("interfaceName");

        RootBeanDefinition definition = new RootBeanDefinition();
        definition.setBeanClass(NettyRpcReference.class);
        definition.setLazyInit(false);

        definition.getPropertyValues().add("interfaceName", interfaceName);

        context.getRegistry().registerBeanDefinition(id,definition);

        return definition;
    }
}
