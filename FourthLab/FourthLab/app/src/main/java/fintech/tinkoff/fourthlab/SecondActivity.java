package fintech.tinkoff.fourthlab;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends Activity {

    TextView firstResult;
    TextView secondResult;

    private Tasks tasks;

    private final static String SUCCESS = "Done!";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        firstResult = (TextView)findViewById(R.id.firstResultView);
        secondResult = (TextView)findViewById(R.id.secondResultView);
        Button button = (Button)findViewById(R.id.secondButton);

        tasks = (Tasks) getLastNonConfigurationInstance();

        if (tasks == null){
            tasks = new Tasks(new FirstTask(), new SecondTask());
            tasks.executeTasks();
        }

        tasks.firstTask.link(this);
        tasks.secondTask.link(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasks.firstTask.cancel(true);
                tasks.secondTask.cancel(true);
                Intent firstActivity = new Intent(SecondActivity.this, FirstActivity.class);
                startActivity(firstActivity);
            }
        });
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        tasks.firstTask.unLink();
        tasks.secondTask.unLink();
        return tasks;
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        tasks.firstTask.cancel(true);
        tasks.secondTask.cancel(true);
    }

    class Tasks{
        public FirstTask firstTask;
        public SecondTask secondTask;

        public Tasks(FirstTask firstTask, SecondTask secondTask){
            this.firstTask = firstTask;
            this.secondTask = secondTask;
        }

        public void executeTasks(){
            firstTask.execute();
            secondTask.execute();
        }
    }

    class FirstTask extends AsyncTask<Void, Void, Void>{

        SecondActivity activity;

        void link(SecondActivity act) {
            activity = act;
        }

        void unLink() {
            activity = null;
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (isCancelled()) {
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            activity.firstResult.setText(SUCCESS);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast toast = Toast.makeText(activity, "Canceled", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    class SecondTask extends AsyncTask<Void, Void, Void>{

        SecondActivity activity;

        void link(SecondActivity act) {
            activity = act;
        }

        void unLink() {
            activity = null;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                for (int i = 0; i < 5; i++) {
                    if (isCancelled()) {
                        return null;
                    }
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            activity.secondResult.setText(SUCCESS);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast toast = Toast.makeText(activity, "Canceled", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
