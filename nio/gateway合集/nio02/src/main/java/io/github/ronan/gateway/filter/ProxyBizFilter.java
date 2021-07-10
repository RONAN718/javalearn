package io.github.ronan.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
public class ProxyBizFilter implements HttpRequestFilter{
    public static ProxyBizFilter newInstance() {
        return new ProxyBizFilter();
    }
    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {

        String uri = fullRequest.uri();
        System.out.println(" proxyBizFilter接收到的请求,url: " + uri);
        HttpHeaders headers = fullRequest.headers();
        if (uri.startsWith("/hello")) {
            System.out.println("支持的uri"+uri);
        } else {
            System.out.println("不支持的uri"+uri);
        }

        if (headers == null) {
            headers = new DefaultHttpHeaders();
        }
    }
}
