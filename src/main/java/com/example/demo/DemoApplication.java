package com.example.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import javax.net.SocketFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Set;

@SpringBootApplication
public class DemoApplication {


    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        Socket socket = SocketFactory.getDefault().createSocket("localhost", 12345);

        ///////////////////////////
        /// Example 1 send and receive message via java socket directly
        ///////////////////////////

        // Send message
        socket.getOutputStream().write("Hello world!\r\n".getBytes());

        // Read message
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("Hello message: " + reader.readLine());


        ///////////////////////////
        /// Example 2 send and receive message via spring channels calling beans manually
        ///////////////////////////

        // Send message to each connection
        Set<String> connections = context.getBean("tcpConnections", Set.class);
        for (String connection : connections) {
            MessageChannel outputChannel = context.getBean("tcpOutputChannel", MessageChannel.class);
            Message<String> message = MessageBuilder.withPayload("Hello message! (using channels)")
                    .setHeader(IpHeaders.CONNECTION_ID, connection)
                    .build();
            outputChannel.send(message);
        }

        // Read message
        System.out.println(reader.readLine());


        reader.close();
        context.close();
    }
}
