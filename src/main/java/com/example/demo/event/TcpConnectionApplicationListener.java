package com.example.demo.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.integration.ip.tcp.connection.TcpConnectionCloseEvent;
import org.springframework.integration.ip.tcp.connection.TcpConnectionEvent;
import org.springframework.integration.ip.tcp.connection.TcpConnectionOpenEvent;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class TcpConnectionApplicationListener implements ApplicationListener<TcpConnectionEvent> {
    private final Logger log = LoggerFactory.getLogger(TcpConnectionApplicationListener.class);
    private final Set<String> connections;

    public TcpConnectionApplicationListener(@Qualifier("tcpConnections") Set<String> connections) {
        this.connections = connections;
    }

    @Override
    public void onApplicationEvent(TcpConnectionEvent event) {
        if (event instanceof TcpConnectionOpenEvent) {
            log.info("Got TCP Open connection event with id={}", event.getConnectionId());
            connections.add(event.getConnectionId());
        } else if (event instanceof TcpConnectionCloseEvent) {
            log.info("Got TCP Close connection event with id={}", event.getConnectionId());
            connections.remove(event.getConnectionId());
        }
    }
}
