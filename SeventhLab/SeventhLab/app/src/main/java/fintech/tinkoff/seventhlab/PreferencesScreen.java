package fintech.tinkoff.seventhlab;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

public class PreferencesScreen extends TabActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences_screen);

        TabHost tabHost = getTabHost();

        TabHost.TabSpec tabSpec;

        tabSpec = tabHost.newTabSpec("parents");
        tabSpec.setIndicator("Parents");
        tabSpec.setContent(new Intent(PreferencesScreen.this, ParentsActivity.class));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("children");
        tabSpec.setIndicator("Children");
        tabSpec.setContent(new Intent(PreferencesScreen.this, ChildrenActivity.class));
        tabHost.addTab(tabSpec);

        Button backButton = (Button) findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreferencesScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
