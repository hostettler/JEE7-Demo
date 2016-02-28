package ch.demo.business.service;

import java.util.List;

import javax.enterprise.inject.Alternative;

@Alternative
public class GradeServiceConsumerMock implements GradeServiceConsumer {
	
	public Double getAvgGradeForStudents(List<String> students) {
		return 10d;
	}
}
