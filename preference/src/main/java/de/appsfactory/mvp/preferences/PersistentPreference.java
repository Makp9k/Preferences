package de.appsfactory.mvp.preferences;

import android.content.SharedPreferences;

/**
 * Created by Collider on 24.06.2017.
 */

public class PersistentPreference<T> extends BasePreference<T> {

    public PersistentPreference(SharedPreferences sharedPreferences, String key, T defaultValue) {
    }

    @Override
    public T get() {
        return super.get();
    }

    @Override
    public void set(T value) {
        super.set(value);
    }
}
