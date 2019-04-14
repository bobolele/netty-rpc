package io.lybo.rpc.netty.send;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class MessageSendChannelInitializer extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel ch) throws Exception {
        // 禁止缓存加载器
       ch.pipeline().addLast("decoder",new ObjectDecoder(1024,
               ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
       ch.pipeline().addLast("encoder",new ObjectEncoder());
        ch.pipeline().addLast("myHanlder", new MessageSendHandler());
    }
}
