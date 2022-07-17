package net.skdziwak.restgen.codegen.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface GenerateAPI {
    String path();
    boolean post() default false;
    boolean put() default false;
    boolean patch() default false;
    boolean get() default false;
    boolean search() default false;
    boolean delete() default false;
    boolean jsonSchema() default false;
}
