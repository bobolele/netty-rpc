package io.lybo.rpc.thread;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {

    private static final AtomicInteger THREAD_NUM = new AtomicInteger(1);

    private final AtomicInteger mThreadNum = new AtomicInteger(1);

    private final String prefix;

    private final boolean daemon;

    private final ThreadGroup threadGroup;

    public NamedThreadFactory() {
        this("thread" + THREAD_NUM.getAndIncrement());

    }
    public NamedThreadFactory(String prefix) {
        this(prefix, false);
    }

    public NamedThreadFactory(String prefix,boolean daemon) {
        this.prefix = StringUtils.isNoneEmpty(prefix) ? prefix + "-thread-" : "";
        this.daemon = daemon;
        SecurityManager s = System.getSecurityManager();
        this.threadGroup = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();

    }

    public Thread newThread(Runnable runnable) {
        String name = this.prefix + mThreadNum.getAndIncrement();
        Thread r = new Thread(threadGroup, runnable, name, 0);
        r.setDaemon(daemon);
        return r;
    }
}
