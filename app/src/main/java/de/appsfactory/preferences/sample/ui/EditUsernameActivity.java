package de.appsfactory.preferences.sample.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import de.appsfactory.preferences.R;
import de.appsfactory.preferences.sample.PreferencesSampleApplication;
import de.appsfactory.preferences.sample.logic.AppSettings;

/**
 * Created by Admin on 25.06.2017.
 */

public class EditUsernameActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_username);

        AppSettings appSettings = PreferencesSampleApplication.getApplication(this).getAppSettings();

        EditText username = findViewById(R.id.username);
        username.setText(appSettings.username().get());

        findViewById(R.id.save_btn).setOnClickListener(v -> {
            appSettings.username().set(username.getText().toString());
            finish();
        });
    }

}
