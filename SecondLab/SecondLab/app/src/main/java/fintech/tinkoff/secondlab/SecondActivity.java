package fintech.tinkoff.secondlab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {
    private final String SUCCESS_CANCEL_MESSAGE_V = "You have canceled an action";
    private final String ERROR_MESSAGE_V = "Something went wrong";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button okButton = (Button)findViewById(R.id.okButton);
        Button cancelButton = (Button)findViewById(R.id.cancelButton);
        TextView message = (TextView)findViewById(R.id.messageTextView);
        final EditText newMessage = (EditText)findViewById(R.id.newMessageEditText);

        Intent intent = getIntent();
        String strMessage = intent.getStringExtra("message");

        if (strMessage != null) {
            if (strMessage.length() > 0) {
                message.setText(strMessage);
            } else {
                message.setText("–//–");
            }
        } else {
            message.setText("–//–");
        }

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent viewIntent = new Intent(SecondActivity.this, MainActivity.class);
                    viewIntent.putExtra("message", newMessage.getText().toString());
                    viewIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    viewIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(viewIntent);
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(),
                            ERROR_MESSAGE_V, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent viewIntent = new Intent(SecondActivity.this, MainActivity.class);
                    viewIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(viewIntent);
                    Toast.makeText(getApplicationContext(),
                            SUCCESS_CANCEL_MESSAGE_V, Toast.LENGTH_LONG).show();
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(),
                            ERROR_MESSAGE_V, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }
}
