package org.elsys.ufg;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Pipe extends MapObject {
    private boolean upConnection;
    private boolean downConnection;
    private boolean leftConnection;
    private boolean rightConnection;
    @Transient
    private LogisticNetwork logisticNetwork;

    public Pipe(Integer startX, Integer startY, Integer endX, Integer endY) {
        super(startX, startY, endX, endY, "pipe-cross", 3);
        this.upConnection = false;
        this.downConnection = false;
        this.leftConnection = false;
        this.rightConnection = false;
        addType("pipe");
    }

    public boolean isUpConnection() {
        return upConnection;
    }

    public void setUpConnection(boolean upConnection) {
        this.upConnection = upConnection;
        updateTexture();
    }

    public boolean isDownConnection() {
        return downConnection;
    }

    public void setDownConnection(boolean downConnection) {
        this.downConnection = downConnection;
        updateTexture();
    }

    public boolean isLeftConnection() {
        return leftConnection;
    }

    public void setLeftConnection(boolean leftConnection) {
        this.leftConnection = leftConnection;
        updateTexture();
    }

    public boolean isRightConnection() {
        return rightConnection;
    }

    public void setRightConnection(boolean rightConnection) {
        this.rightConnection = rightConnection;
        updateTexture();
    }

    public LogisticNetwork getLogisticNetwork() {
        return logisticNetwork;
    }

    public void setLogisticNetwork(LogisticNetwork logisticNetwork) {
        this.logisticNetwork = logisticNetwork;
        this.logisticNetwork.addPipe(this);
    }

    public void connect(Inventory inventory) {
        logisticNetwork.addInventory(inventory);
    }

    public void merge(List<LogisticNetwork> logisticNetworks) {
        for(LogisticNetwork newLogisticNetwork : logisticNetworks) {
            logisticNetwork.addPipes(newLogisticNetwork.getPipes());
            logisticNetwork.addInputs(newLogisticNetwork.getInputs());
            logisticNetwork.addOutputs(newLogisticNetwork.getOutputs());
        }
    }

    public void updateTexture() {
        if((upConnection || downConnection) && !leftConnection && !rightConnection) {
            setTexture("pipe-straight-vertical");
        }

        if(!upConnection && !downConnection && (leftConnection || rightConnection)) {
            setTexture("pipe-straight-horizontal");
        }

        if(upConnection && !downConnection && leftConnection && !rightConnection) {
            setTexture("pipe-corner-up-left");
        }

        if(upConnection && !downConnection && !leftConnection && rightConnection) {
            setTexture("pipe-corner-up-right");
        }

        if(!upConnection && downConnection && leftConnection && !rightConnection) {
            setTexture("pipe-corner-down-left");
        }

        if(!upConnection && downConnection && !leftConnection && rightConnection) {
            setTexture("pipe-corner-down-right");
        }

        if(upConnection && !downConnection && leftConnection && rightConnection) {
            setTexture("pipe-t-up");
        }

        if(!upConnection && downConnection && leftConnection && rightConnection) {
            setTexture("pipe-t-down");
        }

        if(upConnection && downConnection && leftConnection && !rightConnection) {
            setTexture("pipe-t-left");
        }

        if(upConnection && downConnection && !leftConnection && rightConnection) {
            setTexture("pipe-t-right");
        }

        if(upConnection && downConnection && leftConnection && rightConnection) {
            setTexture("pipe-cross");
        }
    }
}
