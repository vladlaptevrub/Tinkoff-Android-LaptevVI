package fintech.tinkoff.secondlab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final String ERROR_MESSAGE_M = "SMS message didn't send";
    private final String ERROR_MESSAGE_V = "View's message didn't send";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button toMButton = (Button)findViewById(R.id.sendToMButton);
        Button toVButton = (Button)findViewById(R.id.sendToVButton);
        TextView message = (TextView)findViewById(R.id.messageFromSecond);
        final EditText toMessenger = (EditText)findViewById(R.id.messageEditText);
        final EditText toView = (EditText)findViewById(R.id.viewEditText);


        Intent intent = getIntent();
        String strMessage = intent.getStringExtra("message");
        if (strMessage != null){
            if (strMessage.length() > 0) {
                message.setText(strMessage);
            } else {
                message.setText("–//–");
            }
        } else {
            message.setText("–//–");
        }

        toMButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                    smsIntent.putExtra("sms_body", toMessenger.getText().toString());
                    smsIntent.setType("vnd.android-dir/mms-sms");
                    startActivity(smsIntent);
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(),
                            ERROR_MESSAGE_M, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        toVButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent viewIntent = new Intent(MainActivity.this, SecondActivity.class);
                    viewIntent.putExtra("message", toView.getText().toString());
                    viewIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    viewIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(viewIntent);

                } catch (Exception e){
                    Toast.makeText(getApplicationContext(),
                            ERROR_MESSAGE_V, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }
}
