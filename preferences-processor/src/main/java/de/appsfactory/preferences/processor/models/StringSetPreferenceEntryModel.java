package de.appsfactory.preferences.processor.models;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lza on 10.12.2017.
 */

public class StringSetPreferenceEntryModel extends PreferenceEntryModel {

    public StringSetPreferenceEntryModel(String name, Object defaultValue, String customAdapter) {
        super(name, "java.util.Set<java.lang.String>", "StringSetPersistentPreference", defaultValue, customAdapter);
    }

    @Override
    public TypeName getType() {
        return ParameterizedTypeName.get(Set.class, String.class);
    }

    @Override
    public String writeDefaultValueInitializer(MethodSpec.Builder builder) {
        String varName = getName() + "DefaultValue";
        if (getDefaultValue() != null) {
            builder.addStatement(
                    "$T $N = new $T($T.asList($S))",
                    getType(),
                    varName,
                    ParameterizedTypeName.get(HashSet.class, String.class),
                    Arrays.class,
                    getDefaultValue()
            );
        } else {
            builder.addStatement(
                    "$T $N = new $T()",
                    getType(),
                    varName,
                    ParameterizedTypeName.get(HashSet.class, String.class)
            );
        }

        return varName;
    }
}
