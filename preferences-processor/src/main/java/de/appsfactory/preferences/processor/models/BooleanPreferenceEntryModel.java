package de.appsfactory.preferences.processor.models;

import com.squareup.javapoet.MethodSpec;

/**
 * Created by lza on 10.12.2017.
 */

public class BooleanPreferenceEntryModel extends PreferenceEntryModel {

    public BooleanPreferenceEntryModel(String name, Object defaultValue, String customAdapter) {
        super(name, "java.lang.Boolean", "BooleanPersistentPreference", defaultValue, customAdapter);
    }

    @Override
    public String writeDefaultValueInitializer(MethodSpec.Builder builder) {
        String varName = getName() + "DefaultValue";
        builder.addStatement(
                "$T $N = $L",
                Boolean.class,
                varName,
                getDefaultValue() == null ? false : getDefaultValue()
        );
        return varName;
    }
}
