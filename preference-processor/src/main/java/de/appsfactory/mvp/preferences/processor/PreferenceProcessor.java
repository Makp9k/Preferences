package de.appsfactory.mvp.preferences.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import de.appsfactory.mvp.preferences.annotations.Preferences;
import de.appsfactory.mvp.preferences.processor.codegen.PreferencesWriter;
import de.appsfactory.mvp.preferences.processor.models.PreferencesModel;
import de.appsfactory.mvp.preferences.processor.visitors.PreferenceEntryVisitor;

/**
 * Created by Collider on 24.06.2017.
 */

@AutoService(Processor.class)
public class PreferenceProcessor extends AbstractProcessor {

    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(Preferences.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        PreferenceEntryVisitor preferenceEntryVisitor = new PreferenceEntryVisitor(processingEnv);

        for (Element element : roundEnvironment.getElementsAnnotatedWith(Preferences.class)) {

            TypeElement typeElement = (TypeElement) element;
            if (isValid(typeElement)) {
                PreferencesModel preferencesModel = preferenceEntryVisitor.visitPreferences(typeElement);
                messager.printMessage(Diagnostic.Kind.WARNING, "RESULT " + preferencesModel);

                JavaFile javaFile = new PreferencesWriter(preferencesModel).brewJava();
                try {
                    javaFile.writeTo(filer);
                } catch (IOException e) {
                    messager.printMessage(
                            Diagnostic.Kind.ERROR,
                            "Unable to write preferences for type " + typeElement + ". " + e.getLocalizedMessage(),
                            typeElement
                    );
                }
            }
        }
        return true;
    }

    private boolean isValid(TypeElement typeElement) {
        if (!typeElement.getKind().isInterface()) {
            messager.printMessage(Diagnostic.Kind.ERROR, typeElement.getQualifiedName() + " is not an interface", typeElement);
            return false;
        }

        if (typeElement.getEnclosingElement().getKind() != ElementKind.PACKAGE) {
            messager.printMessage(Diagnostic.Kind.ERROR, "Nested interfaces are not supported", typeElement);
            return false;
        }

        return true;
    }
}
