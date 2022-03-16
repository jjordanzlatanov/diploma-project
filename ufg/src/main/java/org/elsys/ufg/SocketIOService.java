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
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SocketIOService {
    private static Map<String, SocketIOClient> clients = new ConcurrentHashMap<>();
    private static final String PUSH_DATA_EVENT = "push_data_event";

    @Autowired
    private SocketIOServer socketIOServer;

    @PostConstruct
    private void Startup(){
        start();
    }

    @PreDestroy
    private void Stop(){
        stop();
    }

    public void start(){
        socketIOServer.addConnectListener((client) -> {
            client.sendEvent("message", "You're connected successfully...");
            String userId = client.getSessionId().toString();
            System.out.println("connect " + userId);
            if (userId != null) {
                clients.put(userId, client);
            }
        });

        socketIOServer.addDisconnectListener((client) -> {
            String clientIp = client.getRemoteAddress().toString();
            String userId = client.getSessionId().toString();
            System.out.println("disconnect " + userId);
            if (userId != null) {
                clients.remove(userId);
                client.disconnect();
            }
        });

        socketIOServer.addEventListener("messageToServer", String.class, (client, data, ackRequest) -> {
            System.out.println("Message from client " + data);
        });

        socketIOServer.start();

//        new Thread(() -> {
//            while (true) {
//                try {
//                    // Send broadcast message every 3 seconds
//                    Thread.sleep(3000);
//                    // socketIOServer.getBroadcastOperations().sendEvent("myBroadcast", "Broadcast message " + Instant.now().toString());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    public void stop(){
        if(socketIOServer != null){
            socketIOServer.stop();
            socketIOServer = null;
        }
    }
}
