package fintech.tinkoff.seventhlab;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParentsActivity extends Activity {

    private DBNodesOpenHelper dbNodesOpenDatabaseHelper;
    private RecyclerView mPRecyclerView;
    private RecyclerView.Adapter mPAdapter;
    private RecyclerView.LayoutManager mPLayoutManager;

    private final static int PARENTS = 0;

    private long parentID;
    private List<Long> parentsIDs;

    private EditText input;

    @Override
    protected void onResume() {
        super.onResume();

        dbNodesOpenDatabaseHelper = OpenHelperManager.getHelper(this, DBNodesOpenHelper.class);
        try {
            Dao<Node, Long> dbNodesDao = dbNodesOpenDatabaseHelper.getDao();
            mPAdapter = new PreferencesRecyclerAdapter(dbNodesDao, PARENTS);
            mPRecyclerView.setAdapter(mPAdapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parents);

        mPRecyclerView = (RecyclerView)findViewById(R.id.parents_view);

        mPLayoutManager = new LinearLayoutManager(this);
        mPRecyclerView.setLayoutManager(mPLayoutManager);

        input = new EditText(ParentsActivity.this);
        input.setHint("Add your value");
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        dbNodesOpenDatabaseHelper = OpenHelperManager.getHelper(this, DBNodesOpenHelper.class);
        try {
            Dao<Node, Long> dbNodesDao = dbNodesOpenDatabaseHelper.getDao();
            mPAdapter = new PreferencesRecyclerAdapter(dbNodesDao, PARENTS);
            mPRecyclerView.setAdapter(mPAdapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        mPRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mPRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {
                parentID = getItemID(position);
                AlertDialog dialog = getBuilder().create();
                dialog.show();
            }

            @Override public void onLongItemClick(View view, int position) {

            }
        }));
    }

    private long getItemID(int position){
        return sortParentsList().get(position).getID();
    }

    private AlertDialog.Builder getBuilder(){
        String title = "Add child to the Node (id: " + parentID + "):";

        AlertDialog.Builder builder = new AlertDialog.Builder(ParentsActivity.this);
        builder.setTitle(title)
                .setView(input)
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ViewGroup vParent = (ViewGroup) input.getParent();
                        vParent.removeView(input);
                        dialog.cancel();
                    }
                })
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (input.getText().toString().length() > 0){
                            try {
                                String toastText = "Child (id: " +
                                                addChild(parentID, Integer.parseInt(input.getText().toString())) +
                                                ") is added";

                                Toast toast = Toast.makeText(ParentsActivity.this, toastText, Toast.LENGTH_SHORT);
                                toast.show();
                                input.setText("");
                                ViewGroup vParent = (ViewGroup) input.getParent();
                                vParent.removeView(input);
                                dialog.dismiss();
                            } catch (SQLException e) {
                                Toast toast = Toast.makeText(ParentsActivity.this, "Error", Toast.LENGTH_SHORT);
                                toast.show();
                                ViewGroup vParent = (ViewGroup) input.getParent();
                                vParent.removeView(input);
                                dialog.cancel();
                            }
                        } else {
                            Toast toast = Toast.makeText(ParentsActivity.this, "Sorry, the field was empty", Toast.LENGTH_SHORT);
                            toast.show();
                            ViewGroup vParent = (ViewGroup) input.getParent();
                            vParent.removeView(input);
                        }
                    }
                })
                .setNeutralButton("DELETE NODE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            deleteNode();
                            String text = "Node (id " + parentID + ") deleted";
                            Toast toast = Toast.makeText(ParentsActivity.this, text, Toast.LENGTH_SHORT);
                            toast.show();
                            ViewGroup vParent = (ViewGroup) input.getParent();
                            vParent.removeView(input);
                            dialog.dismiss();
                        } catch (SQLException e){
                            Toast toast = Toast.makeText(ParentsActivity.this, "Error", Toast.LENGTH_SHORT);
                            toast.show();
                            ViewGroup vParent = (ViewGroup) input.getParent();
                            vParent.removeView(input);
                            dialog.cancel();
                        }
                    }
                });
        return builder;
    }

    private long getLastID() throws SQLException {
        Dao<Node, Long> dbNodesDao = dbNodesOpenDatabaseHelper.getDao();
        long lastID;
        if (dbNodesDao.queryForAll().size() > 0) {
            lastID = dbNodesDao.queryForAll().get(dbNodesDao.queryForAll().size() - 1).getID() + 1;
        } else {
            lastID = 0;
        }

        return lastID;
    }

    private long addChild(long parentID, int value) throws SQLException{
        long id = getLastID();
        Dao<Node, Long> dbNodesDao = dbNodesOpenDatabaseHelper.getDao();
        dbNodesDao.queryForId(parentID).addChild(new Node(id, value));
        mPAdapter = new PreferencesRecyclerAdapter(dbNodesDao, PARENTS);
        mPRecyclerView.setAdapter(mPAdapter);
        return id;
    }

    private void deleteNode() throws SQLException{
        Dao<Node, Long> dbNodesDao = dbNodesOpenDatabaseHelper.getDao();
        dbNodesDao.delete(dbNodesDao.queryForId(parentID));
        mPAdapter = new PreferencesRecyclerAdapter(dbNodesDao, PARENTS);
        mPRecyclerView.setAdapter(mPAdapter);
    }

    private List<Node> sortParentsList(){
        List<Node> nodes = null;
        parentsIDs = new ArrayList<>();
        List<Node> newList = new ArrayList<>();
        try {
            Dao<Node, Long> dbNodesDao = dbNodesOpenDatabaseHelper.getDao();
            nodes = dbNodesDao.queryForAll();
            for (Node node : nodes){
                if (node.getChildren().size() > 0){
                    parentsIDs.add(node.getID());
                    newList.add(node);
                }
            }

            for (Node node : nodes){
                if (!checkParentID(node)){
                    newList.add(node);
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
}
