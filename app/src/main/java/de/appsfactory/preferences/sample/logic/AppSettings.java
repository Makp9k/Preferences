package de.appsfactory.preferences.sample.logic;

import java.util.Set;

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

    @PreferenceOptions(defaultStringSetValue = {"Default string 1", "Default string 2"})
    Preference<Set<String>> strings();

}
