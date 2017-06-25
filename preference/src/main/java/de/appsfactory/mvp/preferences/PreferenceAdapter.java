package de.appsfactory.mvp.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Admin on 25.06.2017.
 */

public interface PreferenceAdapter<T> {
    void init(Context context);

    T read(SharedPreferences sharedPreferences, String key, T defaultValue);

    void write(SharedPreferences sharedPreferences, String key, T value);
}
