package org.nobody.testsnp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;

public class Server {
    private final int port;
    private static final Logger logger;


    static {
        logger = LogManager.getLogger("SSServer");
    }

    public Server(final int port) {
        this.port = port;
        logger.info("started on port" + port);
        String path = new File("").getAbsolutePath();
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(
                            new ChannelInitializer<SocketChannel>() { // (4)
                                @Override
                                public void initChannel(SocketChannel ch) throws Exception {
                                    ch.pipeline().addLast(
                                            new ChannelHandler() {
                                                @Override
                                                public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
                                                    logger.info("handler added");
                                                }

                                                @Override
                                                public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
                                                    logger.info("handler removed");
                                                }

                                                @Override
                                                public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
                                                    logger.error("get Exception", throwable);
                                                    //channelHandlerContext.fireExceptionCaught(throwable);
                                                }
                                            });
                                    ch.pipeline().addLast(new JsonObjectDecoder());
                                    ch.pipeline().addLast(new Handler());
                                }
                            })
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); // (7)

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
        int port = 8080;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new Server(port).run();
    }
}
