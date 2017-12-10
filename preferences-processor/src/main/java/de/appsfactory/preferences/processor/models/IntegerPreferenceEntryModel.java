package de.appsfactory.preferences.processor.models;

import com.squareup.javapoet.MethodSpec;

/**
 * Created by lza on 10.12.2017.
 */

public class IntegerPreferenceEntryModel extends PreferenceEntryModel {

    public IntegerPreferenceEntryModel(String name, Object defaultValue, String customAdapter) {
        super(name, "java.lang.Integer", "IntegerPersistentPreference", defaultValue, customAdapter);
    }

    @Override
    public String writeDefaultValueInitializer(MethodSpec.Builder builder) {
        String varName = getName() + "DefaultValue";
        builder.addStatement(
                "$T $N = $L",
                Integer.class,
                varName,
                getDefaultValue() == null ? 0 : getDefaultValue()
        );
        return varName;
    }
}
