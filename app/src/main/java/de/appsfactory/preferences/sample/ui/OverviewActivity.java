package de.appsfactory.preferences.sample.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.appsfactory.preferences.Preference;
import de.appsfactory.preferences.R;
import de.appsfactory.preferences.sample.PreferencesSampleApplication;
import de.appsfactory.preferences.sample.logic.AppSettings;

/**
 * Created by Admin on 25.06.2017.
 */

public class OverviewActivity extends Activity {

    private List<Preference.Connection<?>> mConnections = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_overview);

        AppSettings appSettings = PreferencesSampleApplication.getApplication(this).getAppSettings();

        //TRACKING
        Switch trackingSwitch = findViewById(R.id.tracking_switch);
        trackingSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                appSettings.isTrackingEnabled().set(isChecked)
        );

        mConnections.add(appSettings.isTrackingEnabled().observe((preference, value) -> {
            trackingSwitch.setChecked(value);
        }));

        //GENDER
        Spinner genderSpinner = findViewById(R.id.gender_spinner);
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                appSettings.gender().set(parent.getAdapter().getItem(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mConnections.add(appSettings.gender().observe((preference, value) -> {
            if (value == null) return;
            if (value.equals("Neutral")) genderSpinner.setSelection(0);
            if (value.equals("Male")) genderSpinner.setSelection(1);
            if (value.equals("Female")) genderSpinner.setSelection(2);
        }));

        //USERNAME
        TextView username = findViewById(R.id.username);
        username.setOnClickListener(v -> {
            startActivity(new Intent(this, EditUsernameActivity.class));
        });

        mConnections.add(appSettings.username().observe((preference, value) ->
                username.setText(value))
        );
    }

    @Override
    protected void onDestroy() {
        for (Preference.Connection<?> connection : mConnections) {
            connection.dispose();
        }
        super.onDestroy();
    }
}
