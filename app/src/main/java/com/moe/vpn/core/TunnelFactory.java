package com.moe.vpn.core;

import com.moe.vpn.tunnel.Config;
import com.moe.vpn.tunnel.RawTunnel;
import com.moe.vpn.tunnel.Tunnel;
import com.moe.vpn.tunnel.httpconnect.HttpConnectConfig;
import com.moe.vpn.tunnel.httpconnect.HttpConnectTunnel;

import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class TunnelFactory {

    public static Tunnel wrap(SocketChannel channel, Selector selector) {
        return new RawTunnel(channel, selector);
    }

    public static Tunnel createTunnelByConfig(InetSocketAddress destAddress, Selector selector) throws Exception {
        if (destAddress.isUnresolved()) {
            Config config = ProxyConfig.Instance.getDefaultTunnelConfig(destAddress);
            if (config instanceof HttpConnectConfig) {
                return new HttpConnectTunnel((HttpConnectConfig) config, selector);
            } 
            throw new Exception("The config is unknow.");
        } else {
            return new RawTunnel(destAddress, selector);
        }
    }

}
