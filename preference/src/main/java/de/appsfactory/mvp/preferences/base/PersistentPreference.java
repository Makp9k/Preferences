package de.appsfactory.mvp.preferences.base;

import android.content.SharedPreferences;

/**
 * Created by Collider on 24.06.2017.
 */

public class PersistentPreference<T> extends BasePreference<T> {

    protected final SharedPreferences sharedPreferences;
    protected final String key;
    protected final T defaultValue;

    public PersistentPreference(SharedPreferences sharedPreferences, String key, T defaultValue) {
        this.sharedPreferences = sharedPreferences;
        this.key = key;
        this.defaultValue = defaultValue;
    }
}
