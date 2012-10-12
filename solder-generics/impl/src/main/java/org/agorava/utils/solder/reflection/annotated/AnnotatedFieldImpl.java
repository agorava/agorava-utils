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
package org.agorava.utils.solder.reflection.annotated;

import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * @author Stuart Douglas
 * @author Ove Ranheim
 */
class AnnotatedFieldImpl<X> extends AnnotatedMemberImpl<X, Field> implements AnnotatedField<X> {

    AnnotatedFieldImpl(AnnotatedType<X> declaringType, Field field, AnnotationStore annotations, Type overridenType) {
        super(declaringType, field, field.getType(), annotations, field.getGenericType(), overridenType);
    }

}
