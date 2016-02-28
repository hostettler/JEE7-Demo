package ch.demo.business.service;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import ch.demo.dom.Student;

@Path("/students")
public class StudentServiceRs {

	@Inject
	private StudentService service;

	@GET
	@Path("/nbStudents")
	@Produces({ "application/json" })
	public String getNbStudent() {
		return "{\"nbStudents\":\"" + service.getNbStudent() + "\"}";
	}

	@GET
	@Path("/all")
	@Produces({ "application/json" })
	public Response getAll() {
		return Response.ok().entity(service.getAll()).build();
	}

	@GET
	@Path("/byStudentId/{studentId}")
	@Produces({ "application/json" })
	public Response getStudentsByStudentId(
			@NotNull @Digits(integer = 7, fraction = 0) @PathParam("studentId") String studentId) {
		Student s = null;
		try {
			s = service.getStudentByKey(studentId);
		} catch (NoResultException e) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok().entity(s).build();
	}

	@PUT
	@Path("/add")
	@Produces({ "application/json" })
	public Response add(@NotNull Student student) throws URISyntaxException {
		service.add(student);
		return Response.status(201).contentLocation(new URI("students/byStudentId/" + student.getStudentId())).build();
	}

	@POST
	@Path("/update/{studentId}")
	@Produces({ "application/json" })
	public Response update(@NotNull @Digits(integer = 7, fraction = 0) @PathParam("studentId") String studentId,
			@NotNull Student student) throws URISyntaxException {
		try {
			service.update(studentId, student);
			//Here there was a bug because I forgot that EJBException swallow RuntimeException and NoResultException is a Runtime.
			//To solve that problem I defined NoResultException as an ApplicationException in the ejb-jar.xml
		} catch (NoResultException e) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}		
		return Response.status(200).contentLocation(new URI("students/byStudentId/" + student.getStudentId())).build();
	}

}