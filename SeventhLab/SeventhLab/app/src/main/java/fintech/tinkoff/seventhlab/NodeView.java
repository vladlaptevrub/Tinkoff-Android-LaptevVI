package fintech.tinkoff.seventhlab;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;


public class NodeView extends LinearLayout {
    TextView id;
    TextView value;

    public NodeView(Context context, long id, int value) {
        super(context);

        LayoutInflater.from(getContext()).inflate(
                R.layout.node_item, this);

        this.id = (TextView)findViewById(R.id.idLabel);
        this.value = (TextView)findViewById(R.id.valueLabel);
        this.id.setText("id: %s" + String.valueOf(id));
        this.value.setText("value: " + String.valueOf(value));
    }
}
