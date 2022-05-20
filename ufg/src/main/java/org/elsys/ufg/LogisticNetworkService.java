package org.elsys.ufg;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LogisticNetworkService {
    private List<LogisticNetwork> logisticNetworks;

    public LogisticNetworkService() {
        logisticNetworks = new ArrayList<>();
    }

    public UUID createLogisticNetwork() {
        LogisticNetwork logisticNetwork = new LogisticNetwork();
        logisticNetworks.add(logisticNetwork);
        return logisticNetwork.getUuid();
    }
}
