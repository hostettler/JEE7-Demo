package ch.demo.business.service;

import java.util.List;

public class GradeServiceConsumerImpl extends AbstractRestConsumer implements GradeServiceConsumer {
	
	public GradeServiceConsumerImpl() {
		super("http://192.168.99.100:8080/jee7-demo-grade-service/rest/");
	}

	@Override
	public Double getAvgGradeForStudents(List<String> students) {		
		return invokeGetWithPathParam("grades/getAvgForStudents/", Double.class, students);
	}
}
