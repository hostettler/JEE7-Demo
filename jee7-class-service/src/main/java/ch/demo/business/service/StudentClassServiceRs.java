package ch.demo.business.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import ch.demo.dom.StudentClass;

@Path("/classes")
public class StudentClassServiceRs {

	@Inject
	private StudentClassService service;

	@PUT
	@Path("/createClass")
	@Produces({ "application/json" })
	public Response createClass(StudentClass sc) throws URISyntaxException {
		service.createClass(sc);
		return Response.status(201).contentLocation(new URI("classes/getStudentClass/" + sc.getClassId())).build();
	}
	
	@PUT
	@Path("/addStudentToClass/{classId}/{studentId}")
	@Produces({ "application/json" })
	public Response addStudentToClass(@PathParam("classId") String classId, @PathParam("studentId") String studentId)  {
		service.addStudentToClass(studentId, classId);		
		return Response.ok().build();
	}
	
	@GET
	@Path("/getStudentClass/{studentClassId}")
	@Produces({ "application/json" })
	public Response getStudentClass(@PathParam("studentClassId") String studentClassId) {
		StudentClass sc = null;
		try {
			sc = service.getStudentClass(studentClassId);
		} catch (NoResultException e) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok().entity(sc).build();
	}

	@GET
	@Path("/getStudentsOfClass/{studentClassId}")
	@Produces({ "application/json" })
	public Response getStudentsOfClass(@PathParam("studentClassId") String studentClassId) {
		List<String> students = null;
		try {
			students = service.getStudentsOfClass(studentClassId);
		} catch (NoResultException e) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok().entity(students).build();
	}
	@GET
	@Path("/getAvgGradeForStudentClass/{studentClassId}")
	@Produces({ "application/json" })
	public Response getAvgGradeForStudentClass(@PathParam("studentClassId") String studentClassId) {
		return Response.ok().entity(service.getAvgForStudentsClass(studentClassId)).build();
	}
}
