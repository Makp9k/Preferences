package de.appsfactory.preferences.processor.models;

import com.squareup.javapoet.MethodSpec;

/**
 * Created by lza on 10.12.2017.
 */

public class FloatPreferenceEntryModel extends PreferenceEntryModel {

    public FloatPreferenceEntryModel(String name, Object defaultValue, String customAdapter) {
        super(name, "java.lang.Float", "FloatPersistentPreference", defaultValue, customAdapter);
    }

    @Override
    public String writeDefaultValueInitializer(MethodSpec.Builder builder) {
        String varName = getName() + "DefaultValue";
        builder.addStatement(
                "$T $N = $L",
                Float.class,
                varName,
                getDefaultValue() == null ? 0F : getDefaultValue()
        );
        return varName;
    }
}
