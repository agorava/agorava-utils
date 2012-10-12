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

import org.agorava.utils.solder.literal.AnyLiteral;
import org.agorava.utils.solder.reflection.Reflections;
import org.agorava.utils.solder.reflection.Synthetic;
import org.apache.deltaspike.core.util.Annotateds;
import org.apache.deltaspike.core.util.metadata.builder.ImmutableInjectionPoint;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.*;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.Map.Entry;

//import org.jboss.solder.bean.ImmutableInjectionPoint;

class GenericManagedBean<T> extends AbstractGenericBean<T> {

    private final InjectionTarget<T> injectionTarget;
    private final Map<AnnotatedField<? super T>, InjectionPoint> injectedFields;
    private final Class<? extends Annotation> scopeOverride;

    GenericManagedBean(Bean<T> originalBean, GenericIdentifier identifier, InjectionTarget<T> injectionTarget, AnnotatedType<T> type, Set<Annotation> qualifiers, Class<? extends Annotation> scopeOverride, Synthetic.Provider annotatedMemberSyntheticProvider, boolean alternative, Class<?> beanClass, BeanManager beanManager) {
        super(originalBean, qualifiers, identifier, Annotateds.createTypeId(type), alternative, beanClass, beanManager);
        this.injectionTarget = injectionTarget;
        this.injectedFields = new HashMap<AnnotatedField<? super T>, InjectionPoint>();
        this.scopeOverride = scopeOverride;
        Set<Annotation> filteredQualifiers = new HashSet<Annotation>(getQualifiers());
        filteredQualifiers.remove(AnyLiteral.INSTANCE);
        for (AnnotatedField<? super T> field : type.getFields()) {
            if (field.isAnnotationPresent(InjectGeneric.class)) {
                if (AnnotatedMember.class.isAssignableFrom(field.getJavaMember().getType())) {
                    injectedFields.put(field, new ImmutableInjectionPoint(field, Collections.<Annotation>singleton(annotatedMemberSyntheticProvider.get(identifier)), this, false, false));
                } else {
                    injectedFields.put(field, new ImmutableInjectionPoint(field, filteredQualifiers, this, false, false));
                }
                if (!field.getJavaMember().isAccessible()) {
                    field.getJavaMember().setAccessible(true);
                }
            }
        }
    }

    @Override
    public T create(CreationalContext<T> creationalContext) {
        T instance = injectionTarget.produce(creationalContext);
        injectionTarget.inject(instance, creationalContext);
        for (Entry<AnnotatedField<? super T>, InjectionPoint> field : injectedFields.entrySet()) {
            Object value = getBeanManager().getInjectableReference(field.getValue(), creationalContext);
            field.getKey().getJavaMember().setAccessible(true);
            Reflections.setFieldValue(field.getKey().getJavaMember(), instance, value);
        }
        injectionTarget.postConstruct(instance);
        return instance;
    }

    // No need to implement destroy(), default will do

    @Override
    public Class<? extends Annotation> getScope() {
        if (scopeOverride != null) {
            return scopeOverride;
        } else {
            return super.getScope();
        }
    }

}
