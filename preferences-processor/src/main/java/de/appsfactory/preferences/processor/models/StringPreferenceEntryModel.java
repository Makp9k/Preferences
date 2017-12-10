package de.appsfactory.preferences.processor.models;

import com.squareup.javapoet.MethodSpec;

/**
 * Created by lza on 10.12.2017.
 */

public class StringPreferenceEntryModel extends PreferenceEntryModel {

    public StringPreferenceEntryModel(String name, Object defaultValue, String customAdapter) {
        super(name, "java.lang.String", "StringPersistentPreference", defaultValue, customAdapter);
    }

    @Override
    public String writeDefaultValueInitializer(MethodSpec.Builder builder) {
        String varName = getName() + "DefaultValue";
        builder.addStatement(
                "$T $N = $L",
                String.class,
                varName,
                getDefaultValue() == null ? "\"\"" : getDefaultValue()
        );
        return varName;
    }
}
