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

import javax.enterprise.event.Reception;
import javax.enterprise.event.TransactionPhase;
import javax.enterprise.inject.spi.ObserverMethod;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;

/**
 * An implementation of {@link ObserverMethod} that forwards all calls to
 * {@link #delegate()}.
 *
 * @param <T> The event type
 * @author Pete Muir
 * @author Ove Ranheim
 */
public abstract class ForwardingObserverMethod<T> implements ObserverMethod<T> {

    /**
     * All calls to this {@link ObserverMethod} instance are forwarded to the
     * delegate unless overridden.
     *
     * @return the delegate {@link ObserverMethod}
     */
    protected abstract ObserverMethod<T> delegate();

    public Class<?> getBeanClass() {
        return delegate().getBeanClass();
    }

    public Set<Annotation> getObservedQualifiers() {
        return delegate().getObservedQualifiers();
    }

    public Type getObservedType() {
        return delegate().getObservedType();
    }

    public Reception getReception() {
        return delegate().getReception();
    }

    public TransactionPhase getTransactionPhase() {
        return delegate().getTransactionPhase();
    }

    public void notify(T event) {
        delegate().notify(event);
    }

    @Override
    public boolean equals(Object obj) {
        return delegate().equals(obj);
    }

    @Override
    public int hashCode() {
        return delegate().hashCode();
    }

    @Override
    public String toString() {
        return delegate().toString();
    }

}
