package de.appsfactory.preferences.processor.visitors;

import com.google.auto.common.AnnotationMirrors;
import com.google.auto.common.MoreElements;

import java.util.HashMap;
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

import de.appsfactory.preferences.annotations.PreferenceOptions;
import de.appsfactory.preferences.processor.models.BooleanPreferenceEntryModel;
import de.appsfactory.preferences.processor.models.CustomPreferenceEntryModel;
import de.appsfactory.preferences.processor.models.FloatPreferenceEntryModel;
import de.appsfactory.preferences.processor.models.IntegerPreferenceEntryModel;
import de.appsfactory.preferences.processor.models.LongPreferenceEntryModel;
import de.appsfactory.preferences.processor.models.PreferenceEntryModel;
import de.appsfactory.preferences.processor.models.PreferencesModel;
import de.appsfactory.preferences.processor.models.StringPreferenceEntryModel;
import de.appsfactory.preferences.processor.models.StringSetPreferenceEntryModel;

/**
 * Created by Collider on 24.06.2017.
 */

public class PreferenceEntryVisitor extends ElementScanner7<Void, PreferencesModel> {

    private static final Map<String, Class> preferenceModelsLookup = new HashMap<>();

    static {
        preferenceModelsLookup.put("java.lang.Integer", IntegerPreferenceEntryModel.class);
        preferenceModelsLookup.put("java.lang.Float", FloatPreferenceEntryModel.class);
        preferenceModelsLookup.put("java.lang.Long", LongPreferenceEntryModel.class);
        preferenceModelsLookup.put("java.lang.Boolean", BooleanPreferenceEntryModel.class);
        preferenceModelsLookup.put("java.lang.String", StringPreferenceEntryModel.class);
        preferenceModelsLookup.put("java.utils.Set<java.lang.String>", StringSetPreferenceEntryModel.class);
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
                    if (!declaredType.asElement().toString().equals("de.appsfactory.preferences.Preference")) {
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
                                "Please try something like de.appsfactory.preferences.Preference<T>",
                        executableElement
                );
            } else {
                String customAdapter = parseCustomAdapter(executableElement);
                preferencesModel.getEntries().add(
                        getPreferenceEntryModelByType(
                                executableElement,
                                type,
                                executableElement.getSimpleName().toString(),
                                parseDefaultValue(executableElement, type),
                                customAdapter
                        )
                );
            }
        }

        return super.visitExecutable(executableElement, preferencesModel);
    }

    private PreferenceEntryModel getPreferenceEntryModelByType(ExecutableElement executableElement,
            String type, String name, Object defaultValue, String customAdapter) {

        switch (type) {
            case "java.lang.Integer":
                return new IntegerPreferenceEntryModel(name, defaultValue, customAdapter);
            case "java.lang.Float":
                return new FloatPreferenceEntryModel(name, defaultValue, customAdapter);
            case "java.lang.Long":
                return new LongPreferenceEntryModel(name, defaultValue, customAdapter);
            case "java.lang.Boolean":
                return new BooleanPreferenceEntryModel(name, defaultValue, customAdapter);
            case "java.lang.String":
                return new StringPreferenceEntryModel(name, defaultValue, customAdapter);
            case "java.util.Set<java.lang.String>":
                return new StringSetPreferenceEntryModel(name, defaultValue, customAdapter);
            default:
                if (customAdapter != null) {
                    return new CustomPreferenceEntryModel(name, type, defaultValue, customAdapter);
                }

                messager.printMessage(
                        Diagnostic.Kind.ERROR,
                        "Don't know how to handle type " + type + " of property " + executableElement + "\n" +
                                "Try to specify custom adapter using the @PreferenceOptions annotation",
                        executableElement
                );
                return null;
        }
    }

    private String parseDefaultValue(ExecutableElement executableElement, String type) {
        PreferenceOptions options = executableElement.getAnnotation(PreferenceOptions.class);
        if (options == null) {
            return null;
        }

        switch (type) {
            case "java.lang.Integer":
                return String.valueOf(options.defaultIntValue());
            case "java.lang.Float":
                return String.valueOf(options.defaultFloatValue());
            case "java.lang.Long":
                return String.valueOf(options.defaultLongValue());
            case "java.lang.Boolean":
                return String.valueOf(options.defaultBooleanValue());
            case "java.lang.String":
                return "\"" + options.defaultStringValue() + "\"";
            case "java.util.Set<java.lang.String>":
                return String.join(", ", options.defaultStringSetValue());
        }
        return null;
    }

    private String parseCustomAdapter(ExecutableElement executableElement) {
        AnnotationMirror annotationMirror = MoreElements.getAnnotationMirror(executableElement, PreferenceOptions.class).orNull();
        if (annotationMirror == null) return null;

        AnnotationValue adapterValue = AnnotationMirrors.getAnnotationValue(annotationMirror, "adapter");
        String adapterClassName = adapterValue.getValue().toString();
        if (adapterClassName.equals("de.appsfactory.preferences.PreferenceAdapter"))
            return null;
        return adapterClassName;
    }
}
