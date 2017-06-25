package de.appsfactory.mvp.preferences.concrete;

import android.content.SharedPreferences;

import java.util.concurrent.atomic.AtomicBoolean;

import de.appsfactory.mvp.preferences.base.PersistentPreference;

/**
 * Created by Admin on 25.06.2017.
 */

public class FloatPersistentPreference extends PersistentPreference<Float> {
    private final AtomicBoolean initialized = new AtomicBoolean(false);

    public FloatPersistentPreference(SharedPreferences sharedPreferences, String key, Float defaultValue) {
        super(sharedPreferences, key, defaultValue);
    }

    @Override
    public Float get() {
        if (!initialized.getAndSet(true)) {
            value.set(sharedPreferences.getFloat(key, defaultValue));
        }
        return super.get();
    }

    @Override
    public void set(Float value) {
        sharedPreferences.edit().putFloat(key, value).apply();
        super.set(value);
    }
}
