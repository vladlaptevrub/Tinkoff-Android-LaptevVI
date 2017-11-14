package fintech.tinkoff.seventhlab;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;

@DatabaseTable(tableName = "nodes_db")

public class Node {
    @DatabaseField(id = true)
    private long id;

    @DatabaseField
    private Integer value;

    @ForeignCollectionField(eager = true)
    private Collection<Node> children;

    @DatabaseField(foreign = true)
    private Node node;


    public Node(){}

    public Node(long id, int value){
        this.id = id;
        this.value = value;
        this.children = new ArrayList<>();
    }

    public long getID(){
        return id;
    }

    public int getValue(){
        return value;
    }

    public void addChild(Node child){
        children.add(child);
    }

    public Collection<Node> getChildren(){
        return children;
    }
}
