package ch.demo.business.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import ch.demo.dom.Grade;

@Path("/grades")
public class GradeServiceRs {

	@Inject
	private GradeService service;

	@PUT
	@Path("/add")
	@Produces({ "application/json" })
	public Response addGrade(Grade grade) throws URISyntaxException {
		service.addGrade(grade);
		return Response.status(201).contentLocation(new URI("grades/byGradeId/" + grade.getId())).build();
	}

	@PUT
	@Path("/delete/{gradeId}")
	@Produces({ "application/json" })
	public Response deleteGrade(@PathParam("gradeId") Long gradeId) {
		try {
			service.deleteGrade(gradeId);
		} catch (EntityNotFoundException | NoResultException e) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok().build();
	}

	@GET
	@Path("/getGradesForStudent/{studentId}")
	@Produces({ "application/json" })
	public Response getAllGradesForStudent(@PathParam("studentId") String studentId) {
		try {
			return Response.ok().entity(service.getAllGradesForStudent(studentId)).build();
		} catch (EntityNotFoundException | NoResultException e) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@GET
	@Path("/getAvgForStudent/{studentId}")
	@Produces({ "application/json" })
	public Response getAvgGradeForStudent(@PathParam("studentId") String studentId) {
		try {
			return Response.ok().entity(service.getTotalAvgForStudent(studentId)).build();
		} catch (EntityNotFoundException | NoResultException e) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@GET
	@Path("/getAvgForStudents/{studentsIds}")
	@Produces({ "application/json" })
	public Response getAvgGradeForStudents(@PathParam("studentsIds") String studentsIds) {
		try {
			return Response.ok().entity(service.getAvgForStudents(Arrays.asList(studentsIds.split(",")))).build();
		} catch (EntityNotFoundException | NoResultException e) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
}
