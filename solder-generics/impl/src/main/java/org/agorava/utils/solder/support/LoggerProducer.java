package org.agorava.utils.solder.support;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.jboss.logging.Logger;

@ApplicationScoped
public class LoggerProducer {

	@Produces
	public Logger produceLogger(InjectionPoint ip) {
		return Logger.getLogger(ip.getMember().getClass());
	}
	
}
