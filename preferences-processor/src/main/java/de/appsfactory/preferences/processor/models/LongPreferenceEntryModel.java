package de.appsfactory.preferences.processor.models;

import com.squareup.javapoet.MethodSpec;

/**
 * Created by lza on 10.12.2017.
 */

public class LongPreferenceEntryModel extends PreferenceEntryModel {

    public LongPreferenceEntryModel(String name, Object defaultValue, String customAdapter) {
        super(name, "java.lang.Long", "LongPersistentPreference", defaultValue, customAdapter);
    }

    @Override
    public String writeDefaultValueInitializer(MethodSpec.Builder builder) {
        String varName = getName() + "DefaultValue";
        builder.addStatement(
                "$T $N = $L",
                Long.class,
                varName,
                getDefaultValue() == null ? 0L : getDefaultValue()
        );
        return varName;
    }
}
