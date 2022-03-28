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
            String userId = client.getSessionId().toString();
            List<GameObject> gameObjects = gameStorageRepository.findGame(username);
            gameService.addGame(username, gameStorageRepository);
            gameService.loadGame(gameObjects, username);
            client.sendEvent("game", gameObjects);
            clientUsernames.put(userId, username);
        });

        socketIOServer.addEventListener("clickLeft", Action.class, (client, action, ackRequest) -> {
            String username = clientUsernames.get(client.getSessionId().toString());

            GameObject gameObject = gameStorageRepository.buildObject(action, username);

            if(gameObject == null){
                return;
            }

            client.sendEvent("build", gameObject);
            gameService.addGameObject(gameObject, username);
        });

        socketIOServer.addEventListener("clickD", Action.class, (client, action, ackRequest) -> {
            String username = clientUsernames.get(client.getSessionId().toString());
            System.out.println(action.getX() + " " + action.getY());
        });

        socketIOServer.start();

        new Thread(() -> {
            while (true) {
                for(String username : clientUsernames.values()){
                    gameService.updateGame(username);
                }

                try {
                    Thread.sleep(3000);
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
