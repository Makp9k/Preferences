package de.appsfactory.preferences.processor.models;

import com.squareup.javapoet.MethodSpec;

/**
 * Created by lza on 10.12.2017.
 */

public class CustomPreferenceEntryModel extends PreferenceEntryModel {

    public CustomPreferenceEntryModel(String name, String type, Object defaultValue, String customAdapter) {
        super(name, type, "CustomPersistentPreference", defaultValue, customAdapter);
    }

    @Override
    public String writeDefaultValueInitializer(MethodSpec.Builder builder) {
        return null;
    }
}
