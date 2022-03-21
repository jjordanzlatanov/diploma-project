package org.elsys.ufg;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SocketIOService {
    private Map<String, SocketIOClient> clients = new ConcurrentHashMap<>();
    private Map<String, String> clientUsernames = new ConcurrentHashMap<>();
    private String PUSH_DATA_EVENT = "push_data_event";

    @Autowired
    private SocketIOServer socketIOServer;

    @Autowired
    GameStorageRepository gameStorageRepository;

    @PostConstruct
    public void start(){
        socketIOServer.addConnectListener((client) -> {
            String userId = client.getSessionId().toString();
            clients.put(userId, client);
        });

        socketIOServer.addDisconnectListener((client) -> {
            String userId = client.getSessionId().toString();
                clients.remove(userId);
                clientUsernames.remove(client.getSessionId().toString());
                client.disconnect();
        });

        socketIOServer.addEventListener("username", String.class, (client, username, ackRequest) -> {
            clientUsernames.put(client.getSessionId().toString(), username);
            client.sendEvent("map", gameStorageRepository.findMap(username));
        });

        socketIOServer.addEventListener("clickLeft", Mouse.class, (client, mouse, ackRequest) -> {
            BurnerDrill burnerDrill = new BurnerDrill(mouse.getRoundX(), mouse.getRoundY(), mouse.getRoundX() + 60, mouse.getRoundY() + 60);
            if(gameStorageRepository.buildObject(burnerDrill, clientUsernames.get(client.getSessionId().toString()))){
                client.sendEvent("build", new BurnerDrill(mouse.getRoundX(), mouse.getRoundY(), mouse.getRoundX() + 60, mouse.getRoundY() + 60));
            }
            burnerDrill.extract();
            System.out.println(burnerDrill.getInventory());
        });

        socketIOServer.start();

//        new Thread(() -> {
//            while (true) {
//                try {
//                    // Send broadcast message every 3 seconds
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    @PreDestroy
    public void stop(){
        if(socketIOServer != null){
            socketIOServer.stop();
            socketIOServer = null;
        }
    }
}
