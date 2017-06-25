package de.appsfactory.mvp.preferences;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.appsfactory.mvp.preferences.settings.AppSettings;
import de.appsfactory.mvp.preferences.settings.AppSettingsImpl;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final TestPreferences preferences = new TestPreferencesImpl(this);
        final AppSettings appSettings = new AppSettingsImpl(this);

        final TextView text = findViewById(R.id.text);
        preferences.appName().observe(new Preference.Observer<String>() {
            @Override
            public void call(Preference<String> preference, String value) {
                text.setText(value + " " + appSettings.uptime().get());
            }
        });


        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.appName().set(preferences.appName().get() + "" + (int) (Math.random() * 10));
            }
        });
    }

}
