package org.elsys.ufg;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
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
    private GameStorageRepository gameStorageRepository;

    @Autowired
    private GameService gameService;

    @PostConstruct
    public void start(){
        socketIOServer.addConnectListener((client) -> {
            clients.put(client.getSessionId().toString(), client);
        });

        socketIOServer.addDisconnectListener((client) -> {
            String userId = client.getSessionId().toString();
            clients.remove(userId);
            gameService.stopGame(clientUsernames.get(userId));
            clientUsernames.remove(userId);
            client.disconnect();
        });

        socketIOServer.addEventListener("username", String.class, (client, username, ackRequest) -> {
            List<MapObject> mapObjects = gameStorageRepository.findMap(username);
            clientUsernames.put(client.getSessionId().toString(), username);
            gameService.addGame(username);
            gameService.loadMap(mapObjects, username);
            client.sendEvent("map", mapObjects);
        });

        socketIOServer.addEventListener("clickLeft", Mouse.class, (client, mouse, ackRequest) -> {
            BurnerDrill burnerDrill = new BurnerDrill(mouse.getRoundX(), mouse.getRoundY(), mouse.getRoundX() + 60, mouse.getRoundY() + 60);
            if(gameStorageRepository.buildObject(burnerDrill, clientUsernames.get(client.getSessionId().toString()))){
                client.sendEvent("build", new BurnerDrill(mouse.getRoundX(), mouse.getRoundY(), mouse.getRoundX() + 60, mouse.getRoundY() + 60));
            }
            gameService.addMapObject(burnerDrill, clientUsernames.get(client.getSessionId().toString()));
        });

        socketIOServer.start();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for(String username : clientUsernames.values()){
                    List<MapObject> mapObjects = gameService.getMapObjects(username);

                    for(MapObject mapObject : mapObjects){
                        if(mapObject instanceof Machine){
                            gameStorageRepository.updateObject(mapObject, username);
                        }
                    }
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
