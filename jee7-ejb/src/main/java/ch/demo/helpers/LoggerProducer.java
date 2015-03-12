package ch.demo.helpers;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Produce a slf4j logger. This is an example of how to particuliar produce a bean
 * that can be injected afterwards.
 * @author hostettler
 *
 */
public class LoggerProducer  implements Serializable {

    /** The serial-id.  */
	private static final long serialVersionUID = -7352371086416154400L;

	/**
     * Produces an instance of a slf4j logger for the given injection point.
     * @param injectionPoint to use
     * @return a logger
     */
    @Produces
    public Logger getLogger(final InjectionPoint injectionPoint) {
    	//The injection point is used to instantiate the correct logger for the
    	//caller class.
    	Bean<?> bean = injectionPoint.getBean();
    	Logger l = null;
    	if (bean != null) {
    		Class<?> beanClass =  bean.getBeanClass();
    		l = LoggerFactory.getLogger(beanClass);
    	} else {
    		l = LoggerFactory.getLogger("Default logger");	
    	}
        return l;
    }

}