package ch.demo.business.service;

import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.inject.Inject;

import org.slf4j.Logger;

@Stateful
@StatefulTimeout(unit = TimeUnit.MINUTES, value = 30)
public class StudentStatisticsServiceImpl implements StudentStatisticsService {

	/**
	 * The logger for the class. The logger is produced by the LoggerProducer
	 * and then injected here
	 */
	@Inject
	private transient Logger logger;

	private Long numberOfAccess = 0l;

	@Override
	public Long getStatistics() {
		return numberOfAccess;
	}

	public void hit() {
		logger.info("Count");
		numberOfAccess++;
	}

	@PrePassivate
	private void prePassivate() {
		logger.info("In PrePassivate method");
	}

	@PostActivate
	private void postActivate() {
		logger.info("In PostActivate method");
	}

	@Remove
	public void clean() {
		logger.info("Remove Things");
		numberOfAccess = 0l;
	}

	@PreDestroy
	private void destroy() {
		logger.info("Destroying the EJB");
	}
}
