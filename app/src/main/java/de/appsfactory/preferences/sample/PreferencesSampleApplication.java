package de.appsfactory.preferences.sample;

import android.app.Application;
import android.content.Context;

import de.appsfactory.preferences.sample.logic.AppSettings;
import de.appsfactory.preferences.sample.logic.AppSettingsImpl;

/**
 * Created by Admin on 25.06.2017.
 */

public class PreferencesSampleApplication extends Application {

    private AppSettings appSettings;

    @Override
    public void onCreate() {
        super.onCreate();

        appSettings = new AppSettingsImpl(this);
    }

    public static PreferencesSampleApplication getApplication(Context context) {
        return (PreferencesSampleApplication) context.getApplicationContext();
    }

    public AppSettings getAppSettings() {
        return appSettings;
    }
}
