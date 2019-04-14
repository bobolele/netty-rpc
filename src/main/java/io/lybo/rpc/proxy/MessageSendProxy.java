package io.lybo.rpc.proxy;

import com.google.common.reflect.AbstractInvocationHandler;
import io.lybo.rpc.model.MessageRequest;
import io.lybo.rpc.netty.send.MessageSendExecutor;

import java.lang.reflect.Method;
import java.util.UUID;

public class MessageSendProxy<T> extends AbstractInvocationHandler {
    @Override
    protected Object handleInvocation(Object o, Method method, Object[] args) throws Throwable {
        MessageRequest request = new MessageRequest();
        request.setMessageId(UUID.randomUUID().toString());
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParamTypes(method.getParameterTypes());
        request.setParams(args);

        Object result = MessageSendExecutor.getInstance().execute(request);
        return result;
    }
}
