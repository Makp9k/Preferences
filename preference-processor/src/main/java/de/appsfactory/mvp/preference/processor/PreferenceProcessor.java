package de.appsfactory.mvp.preference.processor;

import com.google.auto.service.AutoService;

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
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementScanner7;
import javax.tools.Diagnostic;

import de.appsfactory.mvp.preference.annotations.Preferences;

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
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Preferences.class)) {
//            if (isValidPreferences(element)) {
//                ElementScanner7<Void, Void> visitor = new ElementScanner7<Void, Void>() {
//                    @Override
//                    public Void visitExecutable(ExecutableElement executableElement, Void aVoid) {
//                        messager.printMessage(Diagnostic.Kind.NOTE, executableElement.toString(), executableElement);
//                        return super.visitExecutable(executableElement, aVoid);
//                    }
//                };
//                for (Element entry : element.getEnclosedElements()) {
//                    entry.accept(visitor, null);
//                }
//                messager.printMessage(Diagnostic.Kind.NOTE, element.getEnclosedElements().toString(), element);
//            }
            PreferencesVisitor preferencesVisitor = new PreferencesVisitor(processingEnv, (TypeElement) element);
            element.accept(preferencesVisitor, null);
        }
        return true;
    }
}
