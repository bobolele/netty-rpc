package io.lybo.rpc.netty.receiver;

import com.google.common.util.concurrent.*;
import io.lybo.rpc.model.MessageRequest;
import io.lybo.rpc.model.MessageResponse;
import io.lybo.rpc.thread.NamedThreadFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Map;
import java.util.concurrent.*;

public class MessageRecvExecutor {

    // 注册的handler
    private Map<String, Object> handleMap = new ConcurrentHashMap<String, Object>();
    // 处理请求的线程池
    private static volatile ListeningExecutorService threadPoolExecutor =
            MoreExecutors.listeningDecorator(
                    new ThreadPoolExecutor(16,
                            16,
                            0,TimeUnit.MILLISECONDS,
                            new LinkedBlockingDeque<Runnable>(),
                            new NamedThreadFactory("rpc-thread",true),
                            new ThreadPoolExecutor.CallerRunsPolicy()
                    )
            );

    private static MessageRecvExecutor INSTANCE = new MessageRecvExecutor();
    ThreadFactory threadFactory = new NamedThreadFactory("nettyRPC threadFactory");
    EventLoopGroup boss = new NioEventLoopGroup();
    EventLoopGroup workers = new NioEventLoopGroup(4, threadFactory);

    private MessageRecvExecutor() {
        handleMap.clear();

    }

    public static MessageRecvExecutor getInstance() {
        return INSTANCE;
    }

    public void execute(final ChannelHandlerContext ctx, final MessageRequest request, final MessageResponse response) {
        Callable task = new MessageRecvTask(request,response,handleMap.get(request.getClassName()));
        ListenableFuture future = threadPoolExecutor.submit(task);
        Futures.addCallback(future, new FutureCallback() {
            @Override
            public void onSuccess(Object o) {
                ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        System.out.println("return response : message-id : " + request.getMessageId());
                    }
                });
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
            }
        },threadPoolExecutor);

    }

    public void start(String host , int port) {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, workers)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new MessageRecvChannelInitalizer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = bootstrap.bind(host, port).sync();

            future.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println("netty rpc started ...");
                    }
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void stop() {
        workers.shutdownGracefully();
        boss.shutdownGracefully();
    }

    public void regist(String interfaceName , Object service) {
        handleMap.put(interfaceName, service);
    }

}
