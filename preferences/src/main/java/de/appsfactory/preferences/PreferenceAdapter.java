package de.appsfactory.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Admin on 25.06.2017.
 */

public interface PreferenceAdapter<T> {
    void init(Context context);

    T read(SharedPreferences sharedPreferences, String key);

    void write(SharedPreferences sharedPreferences, String key, T value);
}
