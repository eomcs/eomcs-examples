package com.eomcs.netty.ex01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DiscardServer {

  private int port;

  public DiscardServer(int port) {
    this.port = port;
  }

  public void run() throws Exception {
    EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap(); // (2)
      b.group(bossGroup, workerGroup)
      .channel(NioServerSocketChannel.class) // (3)
      .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
        @Override
        public void initChannel(SocketChannel ch) throws Exception {
          ch.pipeline().addLast(new DiscardServerHandler());
        }
      })
      .option(ChannelOption.SO_BACKLOG, 128)          // (5)
      .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

      // Bind and start to accept incoming connections.
      ChannelFuture f = b.bind(port).sync(); // (7)
      System.out.println("서버 실행 중...");

      // Wait until the server socket is closed.
      // In this example, this does not happen, but you can do that to gracefully
      // shut down your server.
      f.channel().closeFuture().sync();
    } finally {
      workerGroup.shutdownGracefully();
      bossGroup.shutdownGracefully();
    }
  }

  public static void main(String[] args) throws Exception {
    new DiscardServer(8888).run();
  }
}

// Netty 라이브러리 준비
// - https://central.sonatype.com/ 사이트에서 'netty-all' 라이브러리 검색
// - Gradle 빌드 스크립트 파일(build.gradle)에 등록
//   예) implementation 'io.netty:netty-all:4.1.96.Final'
// - 'gradle eclipse' 실행
// - Eclipse IDE에서 프로젝트 refresh
//
