package org.nobody.testsnp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.nio.charset.StandardCharsets;


/**
 * Handles a server-side channel.
 */
public class Handler extends ChannelInboundHandlerAdapter {
    private static final Logger logger;

    static {
        logger = LogManager.getLogger("HHHandler");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        //JSONParser parser = new JSONParser();
        //RequestData requestData = (RequestData) msg;
        if (msg instanceof ByteBuf) {
            CharSequence string = ((ByteBuf)msg).toString(StandardCharsets.UTF_8);
            logger.info("IN: " + string);
            //JsonReaderEx ex = new JsonReaderEx(string);
            //getCommandProcessor().processIncomingJson(ex);
        }
        //JSONObject jsonObject = (JSONObject) msg;
        //     jsonObject = (JSONObject)parser.parse((String) msg);
        //
        //ctx.write(jsonObject);
        ctx.flush(); // (2)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}