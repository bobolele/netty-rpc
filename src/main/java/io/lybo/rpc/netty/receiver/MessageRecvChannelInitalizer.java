package io.lybo.rpc.netty.receiver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class MessageRecvChannelInitalizer extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast("encoder", new ObjectEncoder());
        // 设置对象解码器，负责对pojo解析
        // 设置序列化最大为1m，
        // 设置线程安全的WeakReferenceMap对类加载器进行缓存
        // 避免内存泄漏
        ch.pipeline().addLast("decoder",new ObjectDecoder(1024, ClassResolvers
                .weakCachingConcurrentResolver(this.getClass().getClassLoader())));
        ch.pipeline().addLast("myHandler", new MessageRecvHandler());
    }
}
