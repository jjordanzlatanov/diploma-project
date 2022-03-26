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
            gameService.stopGame(clientUsernames.get(userId));

            clients.remove(userId);
            clientUsernames.remove(userId);
            client.disconnect();
        });

        socketIOServer.addEventListener("username", String.class, (client, username, ackRequest) -> {
            clientUsernames.put(client.getSessionId().toString(), username);

            List<MapObject> mapObjects = gameStorageRepository.findMap(username);
            List<GameObject> gameObjects = gameStorageRepository.findGame(username);

            gameService.addGame(username);
            gameService.loadGame(gameObjects, username);

            client.sendEvent("map", mapObjects);
        });

        socketIOServer.addEventListener("clickLeft", Action.class, (client, action, ackRequest) -> {
            String username = clientUsernames.get(client.getSessionId().toString());

            MapObject mapObject = gameStorageRepository.buildObject(action, username);

            if(mapObject == null){
                return;
            }

            client.sendEvent("build", mapObject);
            gameService.addGameObject(mapObject, username);
        });

        socketIOServer.start();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for(String username : clientUsernames.values()){
                    List<GameObject> gameObjects = gameService.getGameObjects(username);

                    for(GameObject gameObject : gameObjects){
                        gameStorageRepository.updateObject(gameObject, username);
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
