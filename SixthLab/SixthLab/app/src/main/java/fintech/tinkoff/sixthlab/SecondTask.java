package fintech.tinkoff.sixthlab;

import java.util.HashMap;

public class SecondTask {
    private String name;
    private HashMap<String, Integer> any_map;

    public SecondTask(String name, HashMap<String, Integer> any_map){
        this.name = name;
        this.any_map = any_map;
    }

    public String getNameField(){
        return name;
    }

    public HashMap<String, Integer> getAnyMap(){
        return any_map;
    }
}
