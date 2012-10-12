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

import javax.enterprise.context.Dependent;
import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p>
 * Defines a set of generic beans which can inject the configuration annotation,
 * any other generic bean for the same configuration type, and the product of
 * the generic producer.
 * </p>
 * <p/>
 * <p>
 * Generic beans must be {@link Dependent} scoped. If you wish to give your
 * generic bean a scope, you should do this on the generic producers which
 * configure the generic bean.
 * </p>
 * <p/>
 * <p>
 * Each generic configuration may be specified at most once.
 * </p>
 *
 * @author Stuart Douglas <stuart@baileyroberts.com.au>
 * @author Pete Muir
 * @author Ove Ranheim
 */
@Retention(RUNTIME)
@Target({METHOD, FIELD, PARAMETER, TYPE})
@Documented
public @interface GenericConfiguration {
    Class<? extends Annotation> value();
}
