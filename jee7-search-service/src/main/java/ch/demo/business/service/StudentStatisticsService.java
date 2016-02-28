package ch.demo.business.service;

import javax.ejb.Local;

@Local
public interface StudentStatisticsService {

	public Long getStatistics();
	
	public void hit();
	
	public void clean();

}
