package de.appsfactory.mvp.preferences.concrete;

import android.content.SharedPreferences;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import de.appsfactory.mvp.preferences.base.PersistentPreference;

/**
 * Created by Admin on 25.06.2017.
 */

public class StringSetPersistentPreference extends PersistentPreference<Set<String>> {
    private final AtomicBoolean initialized = new AtomicBoolean(false);

    public StringSetPersistentPreference(SharedPreferences sharedPreferences, String key, Set<String> defaultValue) {
        super(sharedPreferences, key, defaultValue);
    }

    @Override
    public Set<String> get() {
        if (!initialized.getAndSet(true)) {
            value.set(sharedPreferences.getStringSet(key, defaultValue));
        }
        return super.get();
    }

    @Override
    public void set(Set<String> value) {
        sharedPreferences.edit().putStringSet(key, value).apply();
        super.set(value);
    }
}
