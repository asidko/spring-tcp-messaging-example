package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

@Configuration
public class MessageChannelConfiguration {
    public static final String TCP_OUTPUT_CHANNEL_NAME = "tcpOutputChannel";
    public static final String TCP_INPUT_CHANNEL_NAME = "tcpInputChannel";

    @Bean(name = TCP_OUTPUT_CHANNEL_NAME)
    public MessageChannel tcpOutputChannel() {
        return new DirectChannel();
    }

    @Bean(name = TCP_INPUT_CHANNEL_NAME)
    public SubscribableChannel tcpInputChannel() {
        return new DirectChannel();
    }
}
