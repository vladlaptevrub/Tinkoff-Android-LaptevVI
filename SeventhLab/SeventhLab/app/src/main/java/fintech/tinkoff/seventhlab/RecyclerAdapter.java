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
import java.util.Collection;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Dao<Node, Long> dbNodesDao;
    private List<Long> childrenIDs;

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

    public RecyclerAdapter(Dao<Node, Long> dbNodesDao) {
        this.dbNodesDao = dbNodesDao;
        this.childrenIDs = new ArrayList<>();

    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.node_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        List<Node> todos = null;
        Collection<Node> children;
        boolean isOnlyParent = true;
        try {
            todos = dbNodesDao.queryForAll();
            if (todos.get(position).getChildren().size() > 0){
                children = new ArrayList<>(todos.get(position).getChildren());
                for (Node child : children){
                    childrenIDs.add(child.getID());
                }

                for (Long id : childrenIDs){
                    if (todos.get(position).getID() == id){
                        holder.nodeLayout.setBackgroundColor(Color.RED);
                        holder.idLabel.setTextColor(Color.WHITE);
                        holder.idLabel.setText("id: " + todos.get(position).getID());
                        holder.valueLabel.setTextColor(Color.WHITE);
                        holder.valueLabel.setText("value: " + todos.get(position).getValue());
                        isOnlyParent = false;
                        break;
                    }
                }

                if (isOnlyParent) {
                    holder.nodeLayout.setBackgroundColor(Color.YELLOW);
                    holder.idLabel.setText("id: " + todos.get(position).getID());
                    holder.valueLabel.setText("value: " + todos.get(position).getValue());
                }

            } else {
                boolean isAlone = true;
                for (Long id : childrenIDs){
                    if (todos.get(position).getID() == id){
                        holder.nodeLayout.setBackgroundColor(Color.BLUE);
                        holder.idLabel.setTextColor(Color.WHITE);
                        holder.idLabel.setText("id: " + todos.get(position).getID());
                        holder.valueLabel.setTextColor(Color.WHITE);
                        holder.valueLabel.setText("value: " + todos.get(position).getValue());
                        isAlone = false;
                        break;
                    }
                }

                if (isAlone){
                    holder.idLabel.setText("id: " + todos.get(position).getID());
                    holder.valueLabel.setText("value: " + todos.get(position).getValue());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
}