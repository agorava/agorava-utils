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
package org.agorava.utils.solder.bean.generic;

import org.agorava.utils.solder.reflection.Synthetic;
import org.apache.deltaspike.core.util.Annotateds;
import org.apache.deltaspike.core.util.metadata.builder.ImmutableInjectionPoint;
import org.apache.deltaspike.core.util.metadata.builder.InjectableMethod;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.spi.*;
import java.lang.annotation.Annotation;
import java.util.*;

import static org.agorava.utils.solder.bean.Beans.createInjectionPoints;

//import org.jboss.solder.bean.ImmutableInjectionPoint;
//import org.jboss.solder.reflection.annotated.Annotateds;
//import org.jboss.solder.reflection.annotated.InjectableMethod;

public class GenericProducerMethod<T, X> extends AbstractGenericProducerBean<T> {

    private final InjectableMethod<X> producerMethod;
    private final InjectableMethod<X> disposerMethod;

    GenericProducerMethod(Bean<T> originalBean, GenericIdentifier identifier, AnnotatedMethod<X> method, AnnotatedMethod<X> disposerMethod, final Set<Annotation> qualifiers, final Set<Annotation> genericBeanQualifiers, Class<? extends Annotation> scopeOverride, boolean alternative, Class<?> declaringBeanClass, BeanManager beanManager) {
        super(originalBean, identifier, qualifiers, genericBeanQualifiers, scopeOverride, Annotateds.createCallableId(method), alternative, declaringBeanClass, beanManager);
        List<InjectionPoint> injectionPoints = createInjectionPoints(method, this, beanManager);
        List<InjectionPoint> wrappedInjectionPoints = new ArrayList<InjectionPoint>();
        for (InjectionPoint injectionPoint : injectionPoints) {
            wrappedInjectionPoints.add(wrapInjectionPoint(injectionPoint, genericBeanQualifiers));
        }
        this.producerMethod = new InjectableMethod<X>(method, wrappedInjectionPoints, beanManager);
        if (disposerMethod != null) {
            injectionPoints = createInjectionPoints(disposerMethod, this, beanManager);
            wrappedInjectionPoints = new ArrayList<InjectionPoint>();
            for (InjectionPoint injectionPoint : injectionPoints) {
                wrappedInjectionPoints.add(wrapInjectionPoint(injectionPoint, genericBeanQualifiers));
            }
            this.disposerMethod = new InjectableMethod<X>(disposerMethod, wrappedInjectionPoints, beanManager);
        } else {
            this.disposerMethod = null;
        }
    }

    @Override
    protected T getValue(Object receiver, CreationalContext<T> creationalContext) {
        return producerMethod.invoke(receiver, creationalContext);
    }

    @Override
    public void destroy(T instance, CreationalContext<T> creationalContext) {
        if (disposerMethod != null) {
            disposerMethod.invoke(getReceiver(creationalContext), creationalContext);
        }
    }

    private static InjectionPoint wrapInjectionPoint(InjectionPoint injectionPoint, Set<Annotation> quals) {
        Annotated anotated = injectionPoint.getAnnotated();
        boolean genericInjectionPoint = false;
        if (injectionPoint.getType() instanceof Class<?>) {
            Class<?> c = (Class<?>) injectionPoint.getType();
            genericInjectionPoint = c.isAnnotationPresent(GenericConfiguration.class);
        }
        if (anotated.isAnnotationPresent(Disposes.class) || anotated.isAnnotationPresent(InjectGeneric.class) || genericInjectionPoint) {
            Set<Annotation> newQualifiers = new HashSet<Annotation>();
            newQualifiers.addAll(quals);
            newQualifiers.addAll(injectionPoint.getQualifiers());
            Iterator<Annotation> it = newQualifiers.iterator();
            while (it.hasNext()) {
                Annotation annotation = it.next();
                if (annotation.annotationType().equals(Synthetic.class)) {
                    it.remove();
                } else if (annotation.annotationType().equals(GenericMarker.class)) {
                    it.remove();
                }
            }
            return new ImmutableInjectionPoint((AnnotatedParameter<?>) anotated, newQualifiers, injectionPoint.getBean(), injectionPoint.isTransient(), injectionPoint.isDelegate());
        }
        return injectionPoint;
    }

}
