package io.lybo.rpc.netty.send;

import io.lybo.rpc.config.CommonConfig;
import io.lybo.rpc.config.ErrorTipConfig;
import io.lybo.rpc.exception.InvokeTimeoutException;
import io.lybo.rpc.model.MessageRequest;
import io.lybo.rpc.model.MessageResponse;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageCallback {
    private MessageRequest request;
    private MessageResponse response;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public MessageCallback(MessageRequest request) {
        this.request = request;
    }

    public Object start() {
        try {
            lock.lock();
            await();
            if (this.response == null) {
                return null;
            }
//            判断是否调用成功
//            boolean isSuccess = getInvokeResult(response);
//            if (!isSuccess) {
//                throw new ...
//            }
            if (response.getError() != null && !this.response.getError().isEmpty()) {
                throw new InvokeTimeoutException(this.response.getError());
            }
            return this.response.getResult();
        } finally {
            lock.unlock();
        }
    }

    public void over(MessageResponse response) {
        try {
            lock.lock();
            condition.signal();
            this.response = response;
        }finally {
            lock.unlock();
        }
    }

    private void await() {
        boolean onTime = false;
        try {
            onTime = condition.await(CommonConfig.MESSAGE_CALLBACK_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!onTime) {
            throw new InvokeTimeoutException(ErrorTipConfig.INVOKE_TIMEOUT_MSG);
        }
    }
}
