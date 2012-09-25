package org.jboss.solder.support;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

import org.apache.deltaspike.core.api.provider.BeanManagerProvider;

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
