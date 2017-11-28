package tinkoff.fintech.ninethlab.model;

import java.util.HashMap;

public interface IModel {
    void update();
    HashMap<Long, String> returnData();
}
