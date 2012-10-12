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

package org.agorava.utils.solder.support;

import org.apache.deltaspike.core.api.provider.BeanManagerProvider;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

public class BeanManagerUtils {

    public static BeanManager getBeanManager() {
        BeanManagerProvider provider = BeanManagerProvider.getInstance();
        if (BeanManagerProvider.isActive()) {
            return provider.getBeanManager();
        }
        throw new RuntimeException("Unable to locate the BeanManager");
    }

    public static <T> T getContextualInstance(final Class<T> type) {
        BeanManager beanManager = getBeanManager();
        return getContextualInstance(beanManager, type);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getContextualInstance(final BeanManager beanManager,
                                              final Class<T> type) {
        T result = null;
        Bean<T> bean = (Bean<T>) beanManager
                .resolve(beanManager.getBeans(type));
        if (bean != null) {
            CreationalContext<T> context = beanManager
                    .createCreationalContext(bean);
            if (context != null) {
                result = (T) beanManager.getReference(bean, type, context);
            }
        }
        return result;
    }
}
