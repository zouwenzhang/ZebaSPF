package com.zeba.spf.annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface SpfColumn
{
    String value() default "";
    String name() default "";
    boolean isSave() default true;
    String defString() default "";
    float defFloat() default 0F;
    int defInt() default 0;
    long defLong() default 0L;
    boolean defBoolean() default false;
}
