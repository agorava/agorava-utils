/*
 * Copyright 2012 Agorava
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.agorava.utils.solder.support;

import org.jboss.logging.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


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
