package de.appsfactory.mvp.preferences.settings;

import de.appsfactory.mvp.preferences.Preference;
import de.appsfactory.mvp.preferences.annotations.Preferences;

/**
 * Created by Admin on 25.06.2017.
 */
@Preferences
public interface AppSettings {
    Preference<Integer> uptime();
}
