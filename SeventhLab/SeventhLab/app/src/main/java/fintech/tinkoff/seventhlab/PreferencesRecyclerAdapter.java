package fintech.tinkoff.seventhlab;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PreferencesRecyclerAdapter extends RecyclerView.Adapter<PreferencesRecyclerAdapter.ViewHolder> {

    private Dao<Node, Long> dbNodesDao;
    private List<Node> parentsList;
    private List<Node> childrenList;
    private List<Long> childrenIDs;
    private List<Long> parentsIDs;
    private int key;

    private final static int PARENTS = 0;
    private final static int CHILDREN = 1;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView idLabel;
        public TextView valueLabel;
        public LinearLayout nodeLayout;

        public ViewHolder(View v) {
            super(v);
            idLabel = (TextView) v.findViewById(R.id.idLabel);
            valueLabel = (TextView) v.findViewById(R.id.valueLabel);
            nodeLayout = (LinearLayout)v.findViewById(R.id.itemLayout);
        }
    }

    public PreferencesRecyclerAdapter(Dao<Node, Long> dbNodesDao, int key) {
        this.dbNodesDao = dbNodesDao;
        this.childrenIDs = new ArrayList<>();
        this.parentsList = sortParentsList();
        this.key = key;

    }

    @Override
    public PreferencesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.node_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        switch (key){
            case PARENTS:
                if (checkParentID(parentsList.get(position))){
                    holder.nodeLayout.setBackgroundColor(Color.GREEN);
                    holder.idLabel.setText("id: " + parentsList.get(position).getID());
                    holder.valueLabel.setText("value: " + parentsList.get(position).getValue());
                } else {
                    holder.idLabel.setText("id: " + parentsList.get(position).getID());
                    holder.valueLabel.setText("value: " + parentsList.get(position).getValue());
                }
                break;
            case CHILDREN:
                if (checkChildID(childrenList.get(position))){
                    holder.nodeLayout.setBackgroundColor(Color.GREEN);
                    holder.idLabel.setText("id: " + childrenList.get(position).getID());
                    holder.valueLabel.setText("value: " + childrenList.get(position).getValue());
                } else {
                    holder.idLabel.setText("id: " + childrenList.get(position).getID());
                    holder.valueLabel.setText("value: " + childrenList.get(position).getValue());
                }
                break;
        }

    }

    @Override
    public int getItemCount() {
        try {
            return dbNodesDao.queryForAll().size();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private List<Node> sortParentsList(){
        List<Node> nodes = null;
        parentsIDs = new ArrayList<>();
        childrenIDs = new ArrayList<>();
        childrenList = new ArrayList<>();
        List<Node> newList = new ArrayList<>();
        try {
            nodes = dbNodesDao.queryForAll();
            for (Node node : nodes){
                if (node.getChildren().size() > 0){
                    parentsIDs.add(node.getID());
                    newList.add(node);
                    for (Node child : node.getChildren()){
                        childrenIDs.add(child.getID());
                        childrenList.add(child);
                    }
                }
            }

            for (Node node : nodes){
                if (!checkParentID(node)){
                    newList.add(node);
                }

                if (!checkChildID(node)){
                    childrenList.add(node);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return newList;
    }

    private boolean checkParentID(Node node){
        for (Long parentID : parentsIDs){
            if (node.getID() == parentID){
                return true;
            }
        }
        return false;
    }

    private boolean checkChildID(Node node){
        for (Long childID : childrenIDs){
            if (node.getID() == childID){
                return true;
            }
        }
        return false;
    }
}