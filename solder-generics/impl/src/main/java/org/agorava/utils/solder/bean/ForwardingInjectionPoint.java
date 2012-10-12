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
package org.agorava.utils.solder.bean;

import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.Set;

public abstract class ForwardingInjectionPoint implements InjectionPoint {
    protected abstract InjectionPoint delegate();

    public Annotated getAnnotated() {
        return delegate().getAnnotated();
    }

    public Type getType() {
        return delegate().getType();
    }

    public Set<Annotation> getQualifiers() {
        return delegate().getQualifiers();
    }

    public Bean<?> getBean() {
        return delegate().getBean();
    }

    public Member getMember() {
        return delegate().getMember();
    }

    public boolean isDelegate() {
        return delegate().isDelegate();
    }

    public boolean isTransient() {
        return delegate().isTransient();
    }
}
