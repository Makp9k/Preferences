package de.appsfactory.mvp.preferences.processor.visitors;

import com.google.auto.common.AnnotationMirrors;
import com.google.auto.common.MoreElements;
import com.squareup.javapoet.ClassName;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.ElementScanner7;
import javax.lang.model.util.SimpleTypeVisitor7;
import javax.tools.Diagnostic;

import de.appsfactory.mvp.preferences.annotations.PreferenceOptions;
import de.appsfactory.mvp.preferences.processor.models.PreferenceEntryModel;
import de.appsfactory.mvp.preferences.processor.models.PreferencesModel;

/**
 * Created by Collider on 24.06.2017.
 */

public class PreferenceEntryVisitor extends ElementScanner7<Void, PreferencesModel> {

    private static class FieldType {
        private final String preferenceType;
        private final Object defaultValue;

        FieldType(String preferenceType, Object defaultValue) {
            this.preferenceType = preferenceType;
            this.defaultValue = defaultValue;
        }
    }

    private static final Map<String, FieldType> defaultFieldTypes = new HashMap<>();

    static {
        defaultFieldTypes.put("java.lang.Integer", new FieldType("IntegerPersistentPreference", 0));
        defaultFieldTypes.put("java.lang.Float", new FieldType("FloatPersistentPreference", 0f));
        defaultFieldTypes.put("java.lang.Long", new FieldType("FloatPersistentPreference", 0L));
        defaultFieldTypes.put("java.lang.Boolean", new FieldType("BooleanPersistentPreference", false));
        defaultFieldTypes.put("java.lang.String", new FieldType("StringPersistentPreference", "\"\""));
        defaultFieldTypes.put("java.utils.Set<String>", new FieldType("StringSetPersistentPreference", Collections.emptySet()));
    }

    private final Messager messager;

    private TypeElement originElement;

    public PreferenceEntryVisitor(ProcessingEnvironment environment) {
        messager = environment.getMessager();
    }

    public PreferencesModel visitPreferences(TypeElement element) {
        originElement = element;
        PreferencesModel preferencesModel = new PreferencesModel(
                element.getEnclosingElement().asType().toString(),
                element.getSimpleName().toString()
        );
        visit(element, preferencesModel);
        return preferencesModel;
    }

    @Override
    public Void visitExecutable(ExecutableElement executableElement, PreferencesModel preferencesModel) {
        if (executableElement.getEnclosingElement() == originElement) {
            String type = executableElement.getReturnType().accept(new SimpleTypeVisitor7<String, Void>() {
                @Override
                public String visitDeclared(DeclaredType declaredType, Void aVoid) {
                    String type = declaredType.getTypeArguments().toString();
                    if (!declaredType.asElement().toString().equals("de.appsfactory.mvp.preferences.Preference")) {
                        return null;
                    }
                    if (type.isEmpty()) return null;
                    return type;
                }
            }, null);

            if (type == null) {
                messager.printMessage(
                        Diagnostic.Kind.ERROR,
                        "Do not know how to handle return type: " + executableElement.getReturnType() + "\n" +
                                "Please try something like de.appsfactory.mvp.preferences.Preference<T>",
                        executableElement
                );
            } else {
                String customAdapter = parseCustomAdapter(executableElement);
                preferencesModel.getEntries().add(
                        new PreferenceEntryModel(
                                executableElement.getSimpleName().toString(),
                                type,
                                parsePreferenceTypeName(executableElement, type, customAdapter),
                                parseDefaultValue(executableElement, type),
                                customAdapter)
                );
            }
        }

        return super.visitExecutable(executableElement, preferencesModel);
    }

    private String parsePreferenceTypeName(ExecutableElement executableElement, String type, String customAdapter) {
        if (customAdapter != null) {
            return "CustomPersistentPreference";
        }

        FieldType defaultFieldType = defaultFieldTypes.get(type);
        if (defaultFieldType != null) {
            return defaultFieldType.preferenceType;
        }

        messager.printMessage(
                Diagnostic.Kind.ERROR,
                "Don't know how to handle type " + type + " of property " + executableElement + "\n" +
                        "Try to specify custom adapter using the @PreferenceOptions annotation",
                executableElement
        );

        return null;
    }

    private Object parseDefaultValue(ExecutableElement executableElement, String type) {
        PreferenceOptions options = executableElement.getAnnotation(PreferenceOptions.class);
        if (options == null) {
            FieldType fieldType = defaultFieldTypes.get(type);
            if (fieldType != null) {
                return fieldType.defaultValue;
            }
            return null;
        }

        switch (type) {
            case "java.lang.Integer":
                return options.defaultIntValue();
            case "java.lang.Float":
                return options.defaultFloatValue();
            case "java.lang.Long":
                return options.defaultLongValue();
            case "java.lang.Boolean":
                return options.defaultBooleanValue();
            case "java.lang.String":
                return "\"" + options.defaultStringValue() + "\"";
            case "java.util.Set<String>":
                return new HashSet<>(Arrays.asList(options.defaultStringSetValue()));
        }
        return null;
    }

    private String parseCustomAdapter(ExecutableElement executableElement) {
        AnnotationMirror annotationMirror = MoreElements.getAnnotationMirror(executableElement, PreferenceOptions.class).orNull();
        if (annotationMirror == null) return null;

        AnnotationValue adapterValue = AnnotationMirrors.getAnnotationValue(annotationMirror, "adapter");
        String adapterClassName = adapterValue.getValue().toString();
        if (adapterClassName.equals("de.appsfactory.mvp.preferences.PreferenceAdapter"))
            return null;
        return adapterClassName;
    }
}
