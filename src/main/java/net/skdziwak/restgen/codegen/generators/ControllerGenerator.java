package net.skdziwak.restgen.codegen.generators;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import com.google.googlejavaformat.java.JavaFormatterOptions;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

import java.io.IOException;
import java.io.StringWriter;

public class ControllerGenerator {
    public static String generateController(ControllerSpecification controllerSpecification) {
        Configuration configuration = new Configuration(new Version("2.3.31"));
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(ControllerGenerator.class, "/templates/");

        try (StringWriter stringWriter = new StringWriter()) {
            Template template = configuration.getTemplate("controller.ftl");
            template.process(controllerSpecification, stringWriter);
            String sourceCode = stringWriter.toString();
            JavaFormatterOptions options = JavaFormatterOptions.builder()
                    .style(JavaFormatterOptions.Style.AOSP)
                    .formatJavadoc(true)
                    .build();
            Formatter formatter = new Formatter(options);
            return formatter.formatSource(sourceCode);
        } catch (TemplateException | IOException | FormatterException e) {
            throw new RuntimeException(e);
        }
    }

    public static class ControllerSpecification {
        private String packageName;
        private String className;
        private String serviceClass;
        private Endpoints endpoints;
        private DtoSchema dto;
        private String searchSpecificationClass;
        private String idClass;
        private Boolean endpointsSchema = false;
        private String path;
        private String entityName;

        public String getEntityName() {
            return entityName;
        }

        public void setEntityName(String entityName) {
            this.entityName = entityName;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getServiceClass() {
            return serviceClass;
        }

        public void setServiceClass(String serviceClass) {
            this.serviceClass = serviceClass;
        }

        public Endpoints getEndpoints() {
            return endpoints;
        }

        public void setEndpoints(Endpoints endpoints) {
            this.endpoints = endpoints;
        }

        public DtoSchema getDto() {
            return dto;
        }

        public void setDto(DtoSchema dto) {
            this.dto = dto;
        }

        public String getSearchSpecificationClass() {
            return searchSpecificationClass;
        }

        public void setSearchSpecificationClass(String searchSpecificationClass) {
            this.searchSpecificationClass = searchSpecificationClass;
        }

        public String getIdClass() {
            return idClass;
        }

        public void setIdClass(String idClass) {
            this.idClass = idClass;
        }

        public Boolean getEndpointsSchema() {
            return endpointsSchema;
        }

        public void setEndpointsSchema(Boolean endpointsSchema) {
            this.endpointsSchema = endpointsSchema;
        }
    }

    public static class DtoSchema {
        private String post;
        private String put;
        private String patch;
        private String search;
        private String get;
        private String delete;
        private String view;

        public String getPost() {
            return post;
        }

        public void setPost(String post) {
            this.post = post;
        }

        public String getPut() {
            return put;
        }

        public void setPut(String put) {
            this.put = put;
        }

        public String getPatch() {
            return patch;
        }

        public void setPatch(String patch) {
            this.patch = patch;
        }

        public String getSearch() {
            return search;
        }

        public void setSearch(String search) {
            this.search = search;
        }

        public String getGet() {
            return get;
        }

        public void setGet(String get) {
            this.get = get;
        }

        public String getDelete() {
            return delete;
        }

        public void setDelete(String delete) {
            this.delete = delete;
        }

        public String getView() {
            return view;
        }

        public void setView(String view) {
            this.view = view;
        }
    }

    public static class Endpoints {
        private Boolean post = false;
        private Boolean put = false;
        private Boolean patch = false;
        private Boolean search = false;
        private Boolean get = false;
        private Boolean delete = false;

        public Boolean getPost() {
            return post;
        }

        public void setPost(Boolean post) {
            this.post = post;
        }

        public Boolean getPut() {
            return put;
        }

        public void setPut(Boolean put) {
            this.put = put;
        }

        public Boolean getPatch() {
            return patch;
        }

        public void setPatch(Boolean patch) {
            this.patch = patch;
        }

        public Boolean getSearch() {
            return search;
        }

        public void setSearch(Boolean search) {
            this.search = search;
        }

        public Boolean getGet() {
            return get;
        }

        public void setGet(Boolean get) {
            this.get = get;
        }

        public Boolean getDelete() {
            return delete;
        }

        public void setDelete(Boolean delete) {
            this.delete = delete;
        }
    }
}
