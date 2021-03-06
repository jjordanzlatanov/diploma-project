package org.elsys.ufg;

import com.corundumstudio.socketio.SocketIOClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Repository
public class GameStorageRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CreateService createService;

    @Autowired
    private GameService gameService;

    public void save(GameObject gameObject, String username){
        mongoTemplate.save(gameObject, username);
    }

    public void saveAll(List <? extends GameObject> gameObjects, String username) {
        for(GameObject gameObject : gameObjects) {
            mongoTemplate.save(gameObject, username);
        }
    }

    public List<GameObject> findGame(String username) {
        if(!mongoTemplate.collectionExists(username)) {
            mongoTemplate.insert(mongoTemplate.findAll(GameObject.class, "initialMap"), username);
        }

        return mongoTemplate.find(new Query().addCriteria(Criteria.where("types").in("gameObject")).with(Sort.by(Sort.Direction.ASC, "priority")), GameObject.class, username);
    }

    public GameObject buildObject(Action action, String username, SocketIOClient client) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String objectType = action.getObjectType();

        Pattern pattern = createService.getPattern(objectType);

        int startX = action.getStartX();
        int startY = action.getStartY();
        int endX = startX + pattern.getWidth();
        int endY = startY + pattern.getHeight();
        int priority = pattern.getPriority();

        GameObject intersectingObject = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("types").in("mapObject").and("startX").lt(endX).and("startY").lt(endY).and("endX").gt(startX).and("endY").gt(startY).and("priority").is(priority)), GameObject.class, username);

        if(intersectingObject != null) {
            return null;
        }

        GameObject gameObject = createService.create(objectType, startX, startY, endX, endY);

        if(gameObject.getTypes().contains("extractionMachine")){
            ExtractionMachine extractionMachine = ((ExtractionMachine) gameObject);
            List<RawMaterial> resources = findResources(extractionMachine, username);

            if(resources.size() != 0){
                extractionMachine.setMaterial(resources.get(0));
                extractionMachine.setAmountTiles(resources.size());
            }
        }

        if(gameObject.getTypes().contains("pipe")){
            Pipe pipe = ((Pipe) gameObject);
            //pipe.setLogisticNetwork(new LogisticNetwork());

            Pipe upPipe = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("types").in("pipe").and("startX").is(pipe.getStartX()).and("startY").is(pipe.getStartY() - 30)), Pipe.class, username);
            Pipe downPipe = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("types").in("pipe").and("startX").is(pipe.getStartX()).and("startY").is(pipe.getStartY() + 30)), Pipe.class, username);
            Pipe leftPipe = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("types").in("pipe").and("startX").is(pipe.getStartX() - 30).and("startY").is(pipe.getStartY())), Pipe.class, username);
            Pipe rightPipe = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("types").in("pipe").and("startX").is(pipe.getStartX() + 30).and("startY").is(pipe.getStartY())), Pipe.class, username);

            GameObject upGameObject = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("types").in("mapObject").and("inventory.hasInventory").is(true).and("startX").lte(pipe.getStartX()).and("startY").lt(pipe.getStartY()).and("endX").gte(pipe.getEndX()).and("endY").is(pipe.getEndY() - 30)), GameObject.class, username);
            GameObject downGameObject = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("types").in("mapObject").and("inventory.hasInventory").is(true).and("startX").lte(pipe.getStartX()).and("startY").is(pipe.getStartY() + 30).and("endX").gte(pipe.getEndX()).and("endY").gt(pipe.getEndY())), GameObject.class, username);
            GameObject leftGameObject = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("types").in("mapObject").and("inventory.hasInventory").is(true).and("startX").lt(pipe.getStartX()).and("startY").lte(pipe.getStartY()).and("endX").is(pipe.getEndX() - 30).and("endY").gte(pipe.getEndY())), GameObject.class, username);
            GameObject rightGameObject = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("types").in("mapObject").and("inventory.hasInventory").is(true).and("startX").is(pipe.getStartX() + 30).and("startY").lte(pipe.getStartY()).and("endX").gt(pipe.getEndX()).and("endY").gte(pipe.getEndY())), GameObject.class, username);

            List<Pipe> pipes = new ArrayList<>();
            //Set<LogisticNetwork> logisticNetworks = new HashSet<>();

            if(upPipe != null) {
                pipe.setUpConnection(true);
                upPipe.setDownConnection(true);
                pipes.add(upPipe);
                //logisticNetworks.add(upPipe.getLogisticNetwork());
            }

            if(downPipe != null) {
                pipe.setDownConnection(true);
                downPipe.setUpConnection(true);
                pipes.add(downPipe);
                //logisticNetworks.add(downPipe.getLogisticNetwork());
            }

            if(leftPipe != null) {
                pipe.setLeftConnection(true);
                leftPipe.setRightConnection(true);
                pipes.add(leftPipe);
                //logisticNetworks.add(leftPipe.getLogisticNetwork());
            }

            if(rightPipe != null) {
                pipe.setRightConnection(true);
                rightPipe.setLeftConnection(true);
                pipes.add(rightPipe);
                //logisticNetworks.add(rightPipe.getLogisticNetwork());
            }

//            if(logisticNetworks.size() == 0) {
//                pipe.setLogisticNetwork(new LogisticNetwork());
//            }else if(logisticNetworks.size() == 1){
//                pipe.setLogisticNetwork(logisticNetworks.stream().iterator().next());
//            }else{
//                pipe.setLogisticNetwork(new LogisticNetwork());
//                pipe.merge(logisticNetworks.stream().toList());
//                for(LogisticNetwork oldLogisticNetwork : logisticNetworks.stream().toList()) {
//                    mongoTemplate.remove(new Query().addCriteria(Criteria.where("uuid").is(oldLogisticNetwork.getUuid())), username);
//                }
//            }

            if(upGameObject != null) {
                pipe.setUpConnection(true);
                //pipe.connect(upGameObject.inventory);
            }

            if(downGameObject != null) {
                pipe.setDownConnection(true);
                //pipe.connect(downGameObject.inventory);
            }

            if(leftGameObject != null) {
                pipe.setLeftConnection(true);
                //pipe.connect(leftGameObject.inventory);
            }

            if(rightGameObject != null) {
                pipe.setRightConnection(true);
                //pipe.connect(rightGameObject.inventory);
            }

//            if(logisticNetworks.size() != 1) {
//                gameService.addGameObject(pipe.getLogisticNetwork(), username);
//                save(pipe.getLogisticNetwork(), username);
//            }

            saveAll(pipes, username);
            client.sendEvent("updateTexture", pipes);
        }

        save(gameObject, username);
        return gameObject;
    }

    public List<RawMaterial> findResources(ExtractionMachine extractionMachine, String username){
        return mongoTemplate.find(new Query().addCriteria(Criteria.where("types").in("rawMaterial").and("startX").gte(extractionMachine.getStartX()).lt(extractionMachine.getEndX()).and("startY").gte(extractionMachine.getStartY()).lt(extractionMachine.getEndY()).and("endX").gt(extractionMachine.getStartX()).lte(extractionMachine.getEndX()).and("endY").gt(extractionMachine.getStartY()).lte(extractionMachine.getEndY())), RawMaterial.class, username);
    }

    public GameObject findGameObject(Action action, String username){
        return mongoTemplate.findOne(new Query().addCriteria(Criteria.where("startX").lte(action.getX()).and("startY").lte(action.getY()).and("endX").gte(action.getX()).and("endY").gte(action.getY()).and("priority").gte(3)).with(Sort.by(Sort.Direction.DESC, "priority")), GameObject.class, username);
    }

    public void deleteGameObject(GameObject gameObject, String username, SocketIOClient client) {
        if(gameObject.getTypes().contains("pipe")){
            Pipe pipe = ((Pipe) gameObject);

            Pipe upPipe = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("types").in("pipe").and("startX").is(pipe.getStartX()).and("startY").is(pipe.getStartY() - 30)), Pipe.class, username);
            Pipe downPipe = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("types").in("pipe").and("startX").is(pipe.getStartX()).and("startY").is(pipe.getStartY() + 30)), Pipe.class, username);
            Pipe leftPipe = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("types").in("pipe").and("startX").is(pipe.getStartX() - 30).and("startY").is(pipe.getStartY())), Pipe.class, username);
            Pipe rightPipe = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("types").in("pipe").and("startX").is(pipe.getStartX() + 30).and("startY").is(pipe.getStartY())), Pipe.class, username);

            List<GameObject> gameObjects = new ArrayList<>();

            if(upPipe != null) {
                upPipe.setDownConnection(false);
                gameObjects.add(upPipe);
            }

            if(downPipe != null) {
                downPipe.setUpConnection(false);
                gameObjects.add(downPipe);
            }

            if(leftPipe != null) {
                leftPipe.setRightConnection(false);
                gameObjects.add(leftPipe);
            }

            if(rightPipe != null) {
                rightPipe.setLeftConnection(false);
                gameObjects.add(rightPipe);
            }

            saveAll(gameObjects, username);
            client.sendEvent("updateTexture", gameObjects);
        }

        mongoTemplate.remove(new Query().addCriteria(Criteria.where("uuid").is(gameObject.getUuid())), username);
    }
}


