package de.appsfactory.mvp.preference.processor;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementScanner7;
import javax.tools.Diagnostic;

/**
 * Created by Collider on 24.06.2017.
 */

public class PreferencesVisitor extends ElementScanner7<Void, Void> {

    private final ProcessingEnvironment environment;
    private final Messager messager;
    private final Filer filer;
    private final TypeElement originElement;

    PreferencesVisitor(ProcessingEnvironment environment, TypeElement element) {
        this.environment = environment;
        messager = environment.getMessager();
        filer = environment.getFiler();
        this.originElement = element;
    }

    @Override
    public Void visitType(TypeElement typeElement, Void aVoid) {
        if (isValid(typeElement)) {
            typeElement.accept(new PreferencesEntryVisitor(environment, typeElement), null);
        }
        return super.visitType(typeElement, aVoid);
    }

    private boolean isValid(TypeElement typeElement) {
        if (!typeElement.getKind().isInterface()) {
            messager.printMessage(Diagnostic.Kind.ERROR, typeElement.getQualifiedName() + " is not an interface", typeElement);
            return false;
        }

        if (typeElement.getEnclosingElement().getKind() != ElementKind.PACKAGE) {
            messager.printMessage(Diagnostic.Kind.ERROR, "Only top-level preferences are supported", typeElement);
            return false;
        }
        return true;
    }
}
