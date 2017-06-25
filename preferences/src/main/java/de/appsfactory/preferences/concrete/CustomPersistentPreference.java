package de.appsfactory.preferences.concrete;

import android.content.SharedPreferences;

import java.util.concurrent.atomic.AtomicBoolean;

import de.appsfactory.preferences.PreferenceAdapter;
import de.appsfactory.preferences.base.PersistentPreference;

/**
 * Created by Admin on 25.06.2017.
 */

public class CustomPersistentPreference<T> extends PersistentPreference<T> {
    private final AtomicBoolean initialized = new AtomicBoolean(false);
    private final PreferenceAdapter<T> preferenceAdapter;

    public CustomPersistentPreference(SharedPreferences sharedPreferences, String key, PreferenceAdapter<T> preferenceAdapter) {
        super(sharedPreferences, key, null);
        this.preferenceAdapter = preferenceAdapter;
    }

    @Override
    public T get() {
        if (!initialized.getAndSet(true)) {
            value.set(preferenceAdapter.read(sharedPreferences, key));
        }
        return super.get();
    }

    @Override
    public void set(T value) {
        preferenceAdapter.write(sharedPreferences, key, value);
        super.set(value);
    }
}
