package io.lybo.rpc.netty.receiver;

import io.lybo.rpc.thread.NamedThreadFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.ThreadFactory;

public class MessageRecvExecutor {

    ThreadFactory threadFactory = new NamedThreadFactory("nettyRPC threadFactory");
    EventLoopGroup boss = new NioEventLoopGroup();
    EventLoopGroup workers = new NioEventLoopGroup(4, threadFactory);

    public void start() {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, workers)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new MessageRecvChannelInitalizer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_KEEPALIVE, true);

            int port = 16789;
            String host = "127.0.0.1";

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
}
