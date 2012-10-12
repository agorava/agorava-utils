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
package org.agorava.utils.solder.unwraps;

import org.agorava.utils.solder.reflection.Reflections;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.*;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

//import org.jboss.solder.logging.Logger;

/**
 * An extension that allows the use of {@link Unwraps} methods
 *
 * @author Stuart Douglas
 * @author Ove Ranheim
 */
public class UnwrapsExtension implements Extension {

    private final Set<Bean<?>> beans;

    private final static Logger log = Logger.getLogger(UnwrapsExtension.class.getName());

    private final boolean enabled;

    private final Set<Throwable> problems = new HashSet<Throwable>();

    public UnwrapsExtension() {
        this.beans = new HashSet<Bean<?>>();
        boolean en = true;
        try {
            Reflections.classForName("javassist.util.proxy.MethodHandler", UnwrapsExtension.class.getClassLoader());
        } catch (ClassNotFoundException e) {
            en = false;
            log.fine("Javassist not preset, @Unwraps is disabled");
        }
        enabled = en;
    }

    void processAnnotatedType(@Observes ProcessAnnotatedType<?> type, BeanManager beanManager) {
        for (AnnotatedMethod<?> method : type.getAnnotatedType().getMethods()) {
            if (method.isAnnotationPresent(Unwraps.class)) {
                if (!enabled) {
                    problems.add(new RuntimeException(
                            "Javassist not found on the class path, @Unwraps requires javassist to work. @Unwraps found on "
                                    + type.getAnnotatedType().getJavaClass().getName() + "." + method.getJavaMember().getName()));
                } else {
                    for (Annotation annotation : method.getAnnotations()) {
                        if (beanManager.isScope(annotation.annotationType())) {
                            problems.add(new RuntimeException("@Unwraps producer method declared a scope: " + method));
                        }
                    }

                    // we have a managed producer
                    // lets make a note of it and register it later
                    beans.add(createBean(method, beanManager));
                }
            }
        }
    }

    private static <X> Bean<X> createBean(AnnotatedMethod<X> method, BeanManager beanManager) {
        return new UnwrapsProducerBean<X>(method, beanManager);
    }

    void afterBeanDiscovery(@Observes AfterBeanDiscovery afterBean) {
        for (Bean<?> b : beans) {
            afterBean.addBean(b);
        }
        for (Throwable e : problems) {
            afterBean.addDefinitionError(e);
        }
    }

}
