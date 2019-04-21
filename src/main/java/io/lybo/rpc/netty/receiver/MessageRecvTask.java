package io.lybo.rpc.netty.receiver;

import io.lybo.rpc.model.MessageRequest;
import io.lybo.rpc.model.MessageResponse;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.util.concurrent.Callable;

public class MessageRecvTask implements Callable<Boolean> {
    private MessageRequest request;
    private MessageResponse response;
    private Object service;

    public MessageRecvTask(MessageRequest request, MessageResponse response, Object service) {
        this.request = request;
        this.response = response;
        this.service = service;
    }

    @Override
    public Boolean call() throws Exception {
        try {
            String methodName = request.getMethodName();
            Object[] params = request.getParams();
            // fixme 判断是否执行异常没有处理
            Object result = MethodUtils.invokeMethod(service, methodName, params);
            // fixme 没有判断是否执行成功
            response.setMessageId(request.getMessageId());
            response.setResult(result);
            return true;
        } catch (Throwable t) {
            response.setError(t.getMessage());
            t.printStackTrace();
            return false;
        }

    }
}
