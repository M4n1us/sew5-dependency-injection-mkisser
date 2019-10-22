package dev.kisser.processor;

import dev.kisser.annotations.DependencyConsumer;
import dev.kisser.annotations.DependencyProvider;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.LinkedHashSet;
import java.util.Set;

public class AnnotationProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotataions = new LinkedHashSet<String>();
        annotataions.add(DependencyProvider.class.getCanonicalName());
        annotataions.add(DependencyConsumer.class.getCanonicalName());
        return annotataions;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(DependencyProvider.class)
                .forEach(this::verifyIfNotAnnotatingAnnotation);
        roundEnv.getElementsAnnotatedWith(DependencyConsumer.class)
                .forEach(this::verifyIfNotAnnotatingAnnotation);
    }

    private void verifyIfNotAnnotatingAnnotation(Element element){
        if (element.getKind() == ElementKind.ANNOTATION_TYPE){
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Annotation is annotating other annotation.",
                    element);
        }
    }
}
