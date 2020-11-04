package io.github.kimmking.gateway.outbound.netty4;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class NettyOutboundHandler {
    private String host;
    private int port;
    NettyHttpClient client = new NettyHttpClient();
    public NettyOutboundHandler(String backendUrl) {
        backendUrl = backendUrl.endsWith("/")?backendUrl.substring(0,backendUrl.length()-1):backendUrl;
        String[] backend = backendUrl.split(":");
        host = "127.0.0.1";
        port = 8803;
    }

    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        try {
            client.connect(host, port, fullRequest, ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
