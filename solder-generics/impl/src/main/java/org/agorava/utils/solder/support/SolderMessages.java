package org.agorava.utils.solder.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.enterprise.inject.Typed;

import org.apache.deltaspike.core.api.message.annotation.MessageBundle;
import org.apache.deltaspike.core.api.message.annotation.MessageTemplate;
import org.jboss.logging.Logger;

/*
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
*/

public class SolderMessages {
	
	private static Logger log = Logger.getLogger(SolderMessages.class);

    public String annotationAlreadyPresent(Class<? extends Annotation> annotationType) {
    	return String.format("annotationType %s already present", annotationType); 
    }

    public String annotationNotPresent(Class<? extends Annotation> annotationType) {
    	return String.format("annotationType %s not present", annotationType);
    }

    public String fieldNotPresent(Field field, Class<?> declaringClass) {
    	return String.format("field %s not present on class %s", declaringClass);
    }

    public String methodNotPresent(Method method, Class<?> declaringClass) {
    	return String.format("method %s not present on class %s", declaringClass);
    }

    public String parameterNotPresent(Method method, int parameterPosition, Class<?> declaringClass) {
    	return String.format("parameter %s not present on method %s declared on class %s", declaringClass);
    }

    public String parameterMustNotBeNull(String parameterName) {
    	return String.format("%s parameter must not be null", parameterName);
    }
    
}
