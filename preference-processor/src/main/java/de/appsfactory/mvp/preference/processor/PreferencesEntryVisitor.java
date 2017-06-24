package de.appsfactory.mvp.preference.processor;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementScanner7;
import javax.tools.Diagnostic;

/**
 * Created by Collider on 24.06.2017.
 */

public class PreferencesEntryVisitor extends ElementScanner7<Void, Void> {

    private final Messager messager;
    private final Filer filer;
    private final TypeElement originElement;

    PreferencesEntryVisitor(ProcessingEnvironment environment, TypeElement element) {
        messager = environment.getMessager();
        filer = environment.getFiler();
        originElement = element;
    }

    @Override
    public Void visitExecutable(ExecutableElement executableElement, Void aVoid) {
        if (isValid(executableElement)) {
            messager.printMessage(Diagnostic.Kind.WARNING, "Method: " + executableElement.toString());
        }
        return super.visitExecutable(executableElement, aVoid);
    }

    private boolean isValid(ExecutableElement executableElement) {
        DeclaredType returnType = (DeclaredType) executableElement.getReturnType();

        if (!returnType.asElement().toString().equals("de.appsfactory.mvp.preferences.Preference")) {
            messager.printMessage(
                    Diagnostic.Kind.ERROR,
                    "Return type of " + executableElement.getSimpleName() + " must be de.appsfactory.mvp.preferences.Preference",
                    executableElement
            );
            return false;
        }
        return executableElement.getTypeParameters().isEmpty();
    }
}
