package de.appsfactory.mvp.preferences.concrete;

import android.content.SharedPreferences;

import java.util.concurrent.atomic.AtomicBoolean;

import de.appsfactory.mvp.preferences.base.PersistentPreference;

/**
 * Created by Admin on 25.06.2017.
 */

public class BooleanPersistentPreference extends PersistentPreference<Boolean> {
    private final AtomicBoolean initialized = new AtomicBoolean(false);

    public BooleanPersistentPreference(SharedPreferences sharedPreferences, String key, Boolean defaultValue) {
        super(sharedPreferences, key, defaultValue);
    }

    @Override
    public Boolean get() {
        if (!initialized.getAndSet(true)) {
            value.set(sharedPreferences.getBoolean(key, defaultValue));
        }
        return super.get();
    }

    @Override
    public void set(Boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
        super.set(value);
    }
}
