package de.appsfactory.preferences.concrete;

import android.content.SharedPreferences;

import java.util.concurrent.atomic.AtomicBoolean;

import de.appsfactory.preferences.base.PersistentPreference;

/**
 * Created by Admin on 25.06.2017.
 */

public class StringPersistentPreference extends PersistentPreference<String> {
    private final AtomicBoolean initialized = new AtomicBoolean(false);

    public StringPersistentPreference(SharedPreferences sharedPreferences, String key, String defaultValue) {
        super(sharedPreferences, key, defaultValue);
    }

    @Override
    public String get() {
        if (!initialized.getAndSet(true)) {
            value.set(sharedPreferences.getString(key, defaultValue));
        }
        return super.get();
    }

    @Override
    public void set(String value) {
        sharedPreferences.edit().putString(key, value).apply();
        super.set(value);
    }
}
