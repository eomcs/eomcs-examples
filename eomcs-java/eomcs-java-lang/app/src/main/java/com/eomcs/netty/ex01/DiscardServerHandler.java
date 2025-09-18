package com.eomcs.netty.ex01;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
    System.out.println("DiscardServerHandler.channelRead()");
    ByteBuf in = (ByteBuf) msg;
    try {
      /*
            while (in.isReadable()) { // (1)
                System.out.print((char) in.readByte());
                //System.out.flush();
            }
       */
      System.out.println(in.readCharSequence(in.capacity(), Charset.forName("UTF-8")));
      in.resetReaderIndex();
      ctx.writeAndFlush(msg);

    } finally {
      //in.release(); // (2)
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
    System.out.println("DiscardServerHandler.exceptionCaught()");
    cause.printStackTrace();
    ctx.close();
  }
}