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

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.*;

/**
 * A helper class used to hold annotations on a type or member.
 *
 * @author Stuart Douglas
 * @author Ove Ranheim
 */
class AnnotationStore {

    private final Map<Class<? extends Annotation>, Annotation> annotationMap;
    private final Set<Annotation> annotationSet;

    AnnotationStore(Map<Class<? extends Annotation>, Annotation> annotationMap, Set<Annotation> annotationSet) {
        this.annotationMap = annotationMap;
        this.annotationSet = unmodifiableSet(annotationSet);
    }

    AnnotationStore() {
        this.annotationMap = emptyMap();
        this.annotationSet = emptySet();
    }

    <T extends Annotation> T getAnnotation(Class<T> annotationType) {
        return annotationType.cast(annotationMap.get(annotationType));
    }

    Set<Annotation> getAnnotations() {
        return annotationSet;
    }

    boolean isAnnotationPresent(Class<? extends Annotation> annotationType) {
        return annotationMap.containsKey(annotationType);
    }

}
