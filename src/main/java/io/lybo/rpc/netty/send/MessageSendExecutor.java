package io.lybo.rpc.netty.send;


import io.lybo.rpc.model.MessageRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class MessageSendExecutor {
    private static MessageSendExecutor instance = new MessageSendExecutor();

    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup(2);
    private InetSocketAddress remoteAddress = null;

    private Channel channel;
    private MessageSendHandler handler;

    private MessageSendExecutor() {
        init();
    }

    public static MessageSendExecutor getInstance() {
        return instance;
    }

    private void init() {
        remoteAddress = new InetSocketAddress("127.0.0.1", 16789);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .remoteAddress(remoteAddress);
        bootstrap.handler(new MessageSendChannelInitializer());

        channel = bootstrap.connect().syncUninterruptibly().channel();
        handler = channel.pipeline().get(MessageSendHandler.class);
    }

    public Object execute(final MessageRequest request) {
        return handler.sendRequest(request);
    }

    public void stop() {
        eventLoopGroup.shutdownGracefully();
        channel.closeFuture().syncUninterruptibly();
        eventLoopGroup = null;
        channel = null;
    }
}
