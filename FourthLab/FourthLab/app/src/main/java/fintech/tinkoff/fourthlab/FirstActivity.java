package fintech.tinkoff.fourthlab;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FirstActivity extends Activity {

    ListView listView;
    List<String> listElements;
    ArrayAdapter<String> adapter;
    private FirstTask firstTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        listView = (ListView) findViewById(R.id.listView);
        Button button = (Button)findViewById(R.id.firstButton);

        listElements = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(FirstActivity.this, R.layout.row, listElements);

        listView.setAdapter(adapter);

        firstTask = (FirstTask) getLastNonConfigurationInstance();
        if (firstTask == null){
            firstTask = new FirstTask();
            firstTask.execute(11);
        }

        firstTask.link(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstTask.cancel(true);
                Intent secondActivity = new Intent(FirstActivity.this, SecondActivity.class);
                startActivity(secondActivity);
            }
        });
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        firstTask.unLink();
        return firstTask;
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        firstTask.cancel(true);
    }

    static private class FirstTask extends AsyncTask<Integer, Integer, Void> {

        FirstActivity activity;

        void link(FirstActivity act) {
            activity = act;
        }

        void unLink() {
            activity = null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Integer... params) {
            try {
                publishProgress(1);
                for (int i = 2; i < params[0]; i++){
                    if (isCancelled()){
                        return null;
                    }
                    Thread.sleep(5000);
                    publishProgress(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            activity.adapter.add("Line " + values[0].toString());
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast toast = Toast.makeText(activity, "Canceled", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}


