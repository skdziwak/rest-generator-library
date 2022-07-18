package net.skdziwak.restgen.codegen.processors;

import com.google.auto.service.AutoService;
import net.skdziwak.restgen.codegen.annotations.GenerateAPI;
import net.skdziwak.restgen.codegen.generators.ControllerGenerator;
import net.skdziwak.restgen.core.AbstractRestfulService;
import net.skdziwak.restgen.core.extensions.*;
import net.skdziwak.restgen.core.search.DefaultSearchSpecification;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes(
        "net.skdziwak.restgen.codegen.annotations.GenerateAPI"
)
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class ControllerProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(GenerateAPI.class);
        for (Element element : elements) {
            processType((TypeElement) element);
        }

        return true;
    }

    private void processType(TypeElement typeElement) {
        GenerateAPI annotation = typeElement.getAnnotation(GenerateAPI.class);
        ControllerGenerator.ControllerSpecification controllerSpecification = new ControllerGenerator.ControllerSpecification();
        DeclaredType superclass = (DeclaredType) typeElement.getSuperclass();
        assert superclass.asElement().toString().equals(AbstractRestfulService.class.getName());

        DeclaredType entityType = (DeclaredType) superclass.getTypeArguments().get(0);
        DeclaredType idType = (DeclaredType) superclass.getTypeArguments().get(1);
        String entityQualifiedName = entityType.toString();
        String idName = idType.asElement().toString();
        String entityName = entityQualifiedName.substring(entityQualifiedName.lastIndexOf('.') + 1);
        List<? extends TypeMirror> interfaces = typeElement.getInterfaces();

        String serviceQualifiedName = typeElement.asType().toString();
        String packageName = serviceQualifiedName.substring(0, serviceQualifiedName.lastIndexOf('.'));
        String controllerName = serviceQualifiedName.substring(serviceQualifiedName.lastIndexOf('.') + 1) + "GeneratedController";
        ControllerGenerator.DtoSchema dtoSchema = new ControllerGenerator.DtoSchema();
        ControllerGenerator.Endpoints endpoints = new ControllerGenerator.Endpoints();

        for (TypeMirror anInterface : interfaces) {
            DeclaredType interfaceType = (DeclaredType) anInterface;
            String className = interfaceType.asElement().toString();
            List<? extends TypeMirror> typeArguments = interfaceType.getTypeArguments();

            if (annotation.post() && className.equals(PostRestfulService.class.getName())) {
                endpoints.setPost(true);
                dtoSchema.setView(((DeclaredType) typeArguments.get(2)).asElement().toString());
                dtoSchema.setPost(((DeclaredType) typeArguments.get(3)).asElement().toString());
            } else if (annotation.put() && className.equals(PutRestfulService.class.getName())) {
                endpoints.setPut(true);
                dtoSchema.setView(((DeclaredType) typeArguments.get(2)).asElement().toString());
                dtoSchema.setPut(((DeclaredType) typeArguments.get(3)).asElement().toString());
            } else if (annotation.patch() && className.equals(PatchRestfulService.class.getName())) {
                endpoints.setPatch(true);
                dtoSchema.setView(((DeclaredType) typeArguments.get(2)).asElement().toString());
                dtoSchema.setPatch(((DeclaredType) typeArguments.get(3)).asElement().toString());
            } else if (annotation.get() && className.equals(GetRestfulService.class.getName())) {
                endpoints.setGet(true);
                dtoSchema.setGet(((DeclaredType) typeArguments.get(2)).asElement().toString());
            } else if (annotation.search() && className.equals(SearchRestfulService.class.getName())) {
                endpoints.setSearch(true);
                dtoSchema.setSearch(((DeclaredType) typeArguments.get(2)).asElement().toString());
            } else if (annotation.delete() && className.equals(DeleteRestfulService.class.getName())) {
                endpoints.setDelete(true);
            } else if (className.equals(ViewRestfulService.class.getName())) {
                dtoSchema.setView(((DeclaredType) typeArguments.get(2)).asElement().toString());
            }
        }

        controllerSpecification.setPath(annotation.path());
        controllerSpecification.setPackageName(packageName);
        controllerSpecification.setClassName(controllerName);
        controllerSpecification.setEndpoints(endpoints);
        controllerSpecification.setDto(dtoSchema);
        controllerSpecification.setSearchSpecificationClass(DefaultSearchSpecification.class.getName());
        controllerSpecification.setEntityName(entityName);
        controllerSpecification.setServiceClass(serviceQualifiedName);
        controllerSpecification.setIdClass(idName);
        controllerSpecification.setEndpointsSchema(annotation.jsonSchema());

        try (PrintWriter printWriter = new PrintWriter(processingEnv.getFiler().createSourceFile(packageName + "." + controllerName).openOutputStream())) {
            printWriter.print(ControllerGenerator.generateController(controllerSpecification));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
