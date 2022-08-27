package org.elsys.ufg;

import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketIOServerConfiguration {
    private String host;
    private Integer port;

    @Bean
    public SocketIOServer socketIOServer() {
        host = "localhost";
        port = 3000;

        com.corundumstudio.socketio.Configuration configuration = new com.corundumstudio.socketio.Configuration();
        configuration.setHostname(host);
        configuration.setPort(port);
        return new SocketIOServer(configuration);
    }
}
