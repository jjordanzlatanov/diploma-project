package org.elsys.ufg;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.Socket;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SocketIOService {
    private Map<String, SocketIOClient> clients = new ConcurrentHashMap<>();
    private String PUSH_DATA_EVENT = "push_data_event";
    private SocketIOClient userClient;

    @Autowired
    private SocketIOServer socketIOServer;

    @PostConstruct
    public void start(){
        socketIOServer.addConnectListener((client) -> {
            String userId = client.getSessionId().toString();
            client.sendEvent("textMessage", "You're connected successfully... " + userId);
            userClient = client;
            System.out.println("connect " + userId);
            if (userId != null) {
                clients.put(userId, client);
            }
        });

        socketIOServer.addDisconnectListener((client) -> {
            if(userClient.equals(client)){
                userClient = clients.values().iterator().next();
            }

            String clientIp = client.getRemoteAddress().toString();
            String userId = client.getSessionId().toString();
            System.out.println("disconnect " + userId);
            if (userId != null) {
                clients.remove(userId);
                client.disconnect();
            }
        });

        socketIOServer.addEventListener("ObjectMessage", ChatObject.class, (client, data, ackRequest) -> {
            System.out.println("Message from " + data.getUserName() + ": " + data.getMessage());
        });


        socketIOServer.start();

        new Thread(() -> {
            while (true) {
                try {
                    // Send broadcast message every 3 seconds
                    Thread.sleep(3000);
                    if(userClient != null){
                        userClient.sendEvent("textMessage", "Time " + Instant.now().toString());
                    }
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
            }
        }).start();
    }

    @PreDestroy
    public void stop(){
        if(socketIOServer != null){
            socketIOServer.stop();
            socketIOServer = null;
        }
    }
}
