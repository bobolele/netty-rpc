package io.lybo.rpc.netty.receiver;

import io.lybo.rpc.model.MessageRequest;
import io.lybo.rpc.model.MessageResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MessageRecvHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channel read");
        if (msg instanceof MessageRequest) {
            System.out.println(" messageRequest.messageId : " + ((MessageRequest) msg).getMessageId());
            MessageResponse response = new MessageResponse();
            response.setMessageId(((MessageRequest) msg).getMessageId());
            response.setResult(((MessageRequest) msg).getMessageId() + "---");
            ctx.writeAndFlush(response);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel read complete");
    }
}
