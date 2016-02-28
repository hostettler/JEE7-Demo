/**
 * 
 */
package ch.demo.business.interceptors;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;

/**
 * @author hostettler
 * 
 */
public class PerformanceInterceptor implements Serializable {

	/** default serial id. */
	private static final long serialVersionUID = 1L;

	/**
	 * The logger for the class. The logger is produced by the LoggerProducer
	 * and then injected here
	 */
	@Inject
	private transient Logger mLogger;

	/**
	 * log the actual performance of a method.
	 * 
	 * @param context
	 *            of the call
	 * @return the result of the called method
	 * @throws Exception
	 *             if anything goes wrong
	 */
	@AroundInvoke
	public Object invoke(final InvocationContext context) throws Exception {
		long start = System.currentTimeMillis();

		Object value = context.proceed();

		StringBuilder str = new StringBuilder("******  ");
		str.append(context.getMethod().getName());
		str.append(" took :");
		str.append(System.currentTimeMillis() - start);
		str.append(" ms  ******");

		mLogger.info(str.toString());

		return value;
	}
}
