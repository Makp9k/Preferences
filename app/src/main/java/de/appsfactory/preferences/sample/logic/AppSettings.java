package de.appsfactory.preferences.sample.logic;

import de.appsfactory.preferences.Preference;
import de.appsfactory.preferences.annotations.PreferenceOptions;
import de.appsfactory.preferences.annotations.Preferences;

/**
 * Created by Admin on 25.06.2017.
 */

@Preferences
public interface AppSettings {

    Preference<Boolean> isTrackingEnabled();

    Preference<String> gender();

    @PreferenceOptions(defaultStringValue = "Default username")
    Preference<String> username();

}
