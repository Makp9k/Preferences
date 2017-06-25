package de.appsfactory.preferences.concrete;

import android.content.SharedPreferences;

import java.util.concurrent.atomic.AtomicBoolean;

import de.appsfactory.preferences.base.PersistentPreference;

/**
 * Created by Admin on 25.06.2017.
 */

public class LongPersistentPreference extends PersistentPreference<Long> {
    private final AtomicBoolean initialized = new AtomicBoolean(false);

    public LongPersistentPreference(SharedPreferences sharedPreferences, String key, Long defaultValue) {
        super(sharedPreferences, key, defaultValue);
    }

    @Override
    public Long get() {
        if (!initialized.getAndSet(true)) {
            value.set(sharedPreferences.getLong(key, defaultValue));
        }
        return super.get();
    }

    @Override
    public void set(Long value) {
        sharedPreferences.edit().putLong(key, value).apply();
        super.set(value);
    }
}
