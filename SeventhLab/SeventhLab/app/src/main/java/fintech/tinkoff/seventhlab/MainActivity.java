package fintech.tinkoff.seventhlab;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    private DBNodesOpenHelper dbNodesOpenDatabaseHelper;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private long parentID;

    private EditText input;

    private final static int CHILD = 0;
    private final static int PARENT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Button addButton = (Button)findViewById(R.id.addButton);

        input = new EditText(MainActivity.this);
        input.setHint("Add your value");
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        dbNodesOpenDatabaseHelper = OpenHelperManager.getHelper(this, DBNodesOpenHelper.class);
        try {
            Dao<Node, Long> dbNodesDao = dbNodesOpenDatabaseHelper.getDao();
            mAdapter = new RecyclerAdapter(dbNodesDao);
            mRecyclerView.setAdapter(mAdapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = getBuilder(PARENT).create();
                dialog.show();
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, PreferencesScreen.class);
                startActivity(intent);
                finish();
            }

            @Override public void onLongItemClick(View view, int position) {
                try {
                    parentID = getItemID(position);
                    AlertDialog dialog = getBuilder(CHILD).create();
                    dialog.show();
                }catch (SQLException e){
                    Toast toast = Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }));

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

    private long addNode(int value) throws SQLException{
        long id = getLastID();
        Dao<Node, Long> dbNodesDao = dbNodesOpenDatabaseHelper.getDao();
        dbNodesDao.create(new Node(id, value));
        mAdapter = new RecyclerAdapter(dbNodesDao);
        mRecyclerView.setAdapter(mAdapter);
        return id;
    }

    private long getItemID(int position) throws SQLException{
        Dao<Node, Long> dbNodesDao = dbNodesOpenDatabaseHelper.getDao();
        return dbNodesDao.queryForAll().get(position).getID();
    }

    private long addChild(long parentID, int value) throws SQLException{
        long id = getLastID();
        Dao<Node, Long> dbNodesDao = dbNodesOpenDatabaseHelper.getDao();
        dbNodesDao.queryForId(parentID).addChild(new Node(id, value));
        mAdapter = new RecyclerAdapter(dbNodesDao);
        mRecyclerView.setAdapter(mAdapter);
        return id;
    }

    private AlertDialog.Builder getBuilder(final int key){
        String title = "";

        switch (key){
            case CHILD:
                title = "Add child to the Node (id: " + parentID + "):";
                break;
            case PARENT:
                title = "New node:";
                break;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
                                String toastText = "";
                                switch (key){
                                    case CHILD:
                                        toastText = "Child (id: " +
                                                addChild(parentID, Integer.parseInt(input.getText().toString())) +
                                                ") is added";
                                        break;
                                    case PARENT:
                                        toastText = "Node (id: " +
                                                addNode(Integer.parseInt(input.getText().toString())) +
                                                ") is added";
                                        break;
                                }

                                Toast toast = Toast.makeText(MainActivity.this, toastText, Toast.LENGTH_SHORT);
                                toast.show();
                                input.setText("");
                                ViewGroup vParent = (ViewGroup) input.getParent();
                                vParent.removeView(input);
                                dialog.dismiss();
                            } catch (SQLException e) {
                                Toast toast = Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT);
                                toast.show();
                                ViewGroup vParent = (ViewGroup) input.getParent();
                                vParent.removeView(input);
                                dialog.dismiss();
                            }
                        } else {
                            Toast toast = Toast.makeText(MainActivity.this, "Sorry, the field was empty", Toast.LENGTH_SHORT);
                            toast.show();
                            ViewGroup vParent = (ViewGroup) input.getParent();
                            vParent.removeView(input);
                        }
                    }
                });
        if (key == CHILD){
            builder.setNeutralButton("DELETE NODE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        deleteNode();
                        String text = "Node (id " + parentID + ") deleted";
                        Toast toast = Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT);
                        toast.show();
                        ViewGroup vParent = (ViewGroup) input.getParent();
                        vParent.removeView(input);
                        dialog.dismiss();
                    } catch (SQLException e){
                        Toast toast = Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT);
                        toast.show();
                        ViewGroup vParent = (ViewGroup) input.getParent();
                        vParent.removeView(input);
                        dialog.cancel();
                    }
                }
            });
        }
        return builder;
    }

    private void deleteNode() throws SQLException{
        Dao<Node, Long> dbNodesDao = dbNodesOpenDatabaseHelper.getDao();
        dbNodesDao.delete(dbNodesDao.queryForId(parentID));
        mAdapter = new RecyclerAdapter(dbNodesDao);
        mRecyclerView.setAdapter(mAdapter);
    }
}
