package org.agorava.utils.solder.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.deltaspike.core.api.message.annotation.MessageBundle;
import org.apache.deltaspike.core.api.message.annotation.MessageTemplate;

@MessageBundle
public interface SolderMessages {

    @MessageTemplate("annotationType %s already present")
    public String annotationAlreadyPresent(Class<? extends Annotation> annotationType);

    @MessageTemplate("annotationType %s not present")
    public String annotationNotPresent(Class<? extends Annotation> annotationType);

    @MessageTemplate("field %s not present on class %s")
    public String fieldNotPresent(Field field, Class<?> declaringClass);

    @MessageTemplate("method %s not present on class %s")
    public String methodNotPresent(Method method, Class<?> declaringClass);

    @MessageTemplate("parameter %s not present on method %s declared on class %s")
    public String parameterNotPresent(Method method, int parameterPosition, Class<?> declaringClass);

    @MessageTemplate("%s parameter must not be null")
    public String parameterMustNotBeNull(String parameterName);
    
}
