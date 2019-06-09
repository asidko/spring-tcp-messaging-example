package com.example.demo.messaging;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.example.demo.config.MessageChannelConfiguration.TCP_INPUT_CHANNEL_NAME;

@Component
public class MessagingController {
    @StreamListener(TCP_INPUT_CHANNEL_NAME)
    public void messageListener(Message<?> message) {
        System.out.println("Got message via controller:" + message.getPayload());
    }
}
