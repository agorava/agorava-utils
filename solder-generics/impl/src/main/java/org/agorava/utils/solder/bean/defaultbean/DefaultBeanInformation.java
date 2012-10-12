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
package org.agorava.utils.solder.bean.defaultbean;

import javax.enterprise.util.AnnotationLiteral;
import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Set;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * We use this annotation as a carrier of qualifiers so that other extensions have access to the original qualifiers of the bean
 * (those removed and replaced by synthetic qualifier by the {@link DefaultBeanExtension}).
 *
 * @author Jozef Hartinger
 * @author Ove Ranheim
 */
@Target({TYPE, METHOD, FIELD})
@Retention(RUNTIME)
@Documented
public @interface DefaultBeanInformation {

    @SuppressWarnings("all")
    public static class Literal extends AnnotationLiteral<DefaultBeanInformation> implements DefaultBeanInformation {
        private final Set<Annotation> qualifiers;

        public Literal(Set<Annotation> qualifiers) {
            this.qualifiers = qualifiers;
        }

        public Set<Annotation> getQualifiers() {
            return qualifiers;
        }
    }
}
