package ch.demo.business.service;

import java.util.List;

import javax.json.JsonArray;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class GradeServiceConsumerImpl implements GradeServiceConsumer {
	
	private Client client;
	private WebTarget target;

	public GradeServiceConsumerImpl() {
		client = ClientBuilder.newClient();
		target = client.target("http://192.168.99.100:8080/jee7-demo-grade-service/rest/grades/getGradesForStudent");
	}

	@Override
	public Double getAvgGradeForStudents(List<String> students) {
//		JsonArray response = target.request(MediaType.APPLICATION_JSON).buildPost(students)
//				.get(JsonArray.class);
		return 0d;
	}
}
