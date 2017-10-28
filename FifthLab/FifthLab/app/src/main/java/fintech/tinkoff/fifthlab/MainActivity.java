package fintech.tinkoff.fifthlab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomGraphView customGraphView = new CustomGraphView(this);
        setContentView(customGraphView);

    }
}
