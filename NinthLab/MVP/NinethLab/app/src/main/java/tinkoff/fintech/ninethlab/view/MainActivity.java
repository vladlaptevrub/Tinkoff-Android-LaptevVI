package tinkoff.fintech.ninethlab.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import tinkoff.fintech.ninethlab.R;
import tinkoff.fintech.ninethlab.presenter.Presenter;

public class MainActivity extends AppCompatActivity implements IView{
    private Presenter presenter;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerLayoutManager;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new Presenter(this);

        swipeContainer = (SwipeRefreshLayout)findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateData();
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerLayoutManager);
        recyclerAdapter = new RecyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void updateData() {
        presenter.updateModel();
        Log.i("View:", "Send request to Presenter");
    }

    @Override
    public void updateScreen(List<String> list){
        if (list.size() > 0){
            setAdapter(list);
            makeToast("List is updated");
        } else {
            makeToast("List is empty");
        }
    }

    private void setAdapter(List<String> list){
        recyclerAdapter = new RecyclerAdapter(list);
        recyclerView.setAdapter(recyclerAdapter);
        swipeContainer.setRefreshing(false);
        Log.i("View:", "Updated");
    }

    private void makeToast(String text){
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
