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

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation used to define generic injection points. @Inject is swapped out
 * for @InjectConfiguration on generic beans for any injection points that are controlled by
 * the {@link GenericBeanExtension}.
 * <p/>
 * This is an implementation detail, and should not be referenced outside this
 * extension.
 *
 * @author Stuart Douglas <stuart@baileyroberts.com.au>
 * @author Pete Muir
 * @author Ove Ranheim
 * @see GenericConfiguration
 */
@Retention(RUNTIME)
@Target({METHOD, FIELD, CONSTRUCTOR})
@interface InjectGeneric {
}
