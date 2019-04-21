package io.lybo.rpc.spring.reference;

import com.google.common.reflect.Reflection;
import io.lybo.rpc.proxy.MessageSendProxy;
import org.springframework.beans.factory.FactoryBean;

public class NettyRpcReference implements FactoryBean {
    private String interfaceName;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    @Override
    public Object getObject() throws Exception {
        return Reflection.newProxy(getObjectType(), new MessageSendProxy());
    }

    @Override
    public Class<?> getObjectType() {
        try {
            return this.getClass().getClassLoader().loadClass(interfaceName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
