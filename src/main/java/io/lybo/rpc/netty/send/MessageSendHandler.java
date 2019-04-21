package io.lybo.rpc.netty.send;

import io.lybo.rpc.model.MessageRequest;
import io.lybo.rpc.model.MessageResponse;
import io.lybo.rpc.netty.send.map.CallBackMap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.SocketAddress;

public class MessageSendHandler extends ChannelInboundHandlerAdapter {
    private SocketAddress remoteAddr;
    private volatile Channel channel;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.remoteAddr = this.channel.remoteAddress();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channel read");
        MessageResponse response = (MessageResponse) msg;
        String messageId = (response).getMessageId();
        System.out.println("messageResponse.messageId : " + messageId);
        MessageCallback callback = CallBackMap.getInstance().get(messageId);
        if (callback != null) {
            CallBackMap.getInstance().remove(messageId);
            callback.over(response);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel read complete");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    public Object sendRequest(MessageRequest request) {
        MessageCallback callback = new MessageCallback(request);
        CallBackMap.getInstance().put(request.getMessageId(), callback);
        channel.writeAndFlush(request);
        return callback.start();
    }
}
