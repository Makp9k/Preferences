package de.appsfactory.mvp.preferences.concrete;

import android.content.SharedPreferences;

import java.util.concurrent.atomic.AtomicBoolean;

import de.appsfactory.mvp.preferences.base.PersistentPreference;

/**
 * Created by Admin on 25.06.2017.
 */

public class IntegerPersistentPreference extends PersistentPreference<Integer> {
    private final AtomicBoolean initialized = new AtomicBoolean(false);

    public IntegerPersistentPreference(SharedPreferences sharedPreferences, String key, Integer defaultValue) {
        super(sharedPreferences, key, defaultValue);
    }

    @Override
    public Integer get() {
        if (!initialized.getAndSet(true)) {
            value.set(sharedPreferences.getInt(key, defaultValue));
        }
        return super.get();
    }

    @Override
    public void set(Integer value) {
        sharedPreferences.edit().putInt(key, value).apply();
        super.set(value);
    }
}
