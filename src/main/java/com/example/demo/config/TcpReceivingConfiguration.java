package com.example.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.ip.tcp.TcpReceivingChannelAdapter;
import org.springframework.integration.ip.tcp.TcpSendingMessageHandler;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.integration.support.management.SubscribableChannelManagement;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.example.demo.config.MessageChannelConfiguration.TCP_INPUT_CHANNEL_NAME;
import static com.example.demo.config.MessageChannelConfiguration.TCP_OUTPUT_CHANNEL_NAME;

@Configuration
public class TcpReceivingConfiguration {

    /**
     * Sending channel adapter
     * <p>
     * Set sending via TCP implementation for channel tcpOutputChannel
     */
    @Bean
    @ServiceActivator(inputChannel = TCP_OUTPUT_CHANNEL_NAME)
    public TcpSendingMessageHandler senderAdapter() {
        TcpSendingMessageHandler adapter = new TcpSendingMessageHandler();
        adapter.setConnectionFactory(tcpServerConnectionFactory());
        return adapter;
    }

    /**
     * Receiving channel adapter
     * <p>
     * Redirecting all received messages to channel tcpInputChannel
     */
    @Bean
    public TcpReceivingChannelAdapter receiverAdapter(@Qualifier(TCP_INPUT_CHANNEL_NAME) MessageChannel tcpInputChannel) {
        TcpReceivingChannelAdapter adapter = new TcpReceivingChannelAdapter();
        adapter.setConnectionFactory(tcpServerConnectionFactory());
        adapter.setOutputChannel(tcpInputChannel);
        return adapter;
    }

    @Bean
    public TcpNetServerConnectionFactory tcpServerConnectionFactory() {
        return new TcpNetServerConnectionFactory(12345);
    }

    @Bean
    public Set<String> tcpConnections() {
        return Collections.synchronizedSet(new HashSet<>());
    }
}
