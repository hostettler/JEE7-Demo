package ch.demo.web;

import java.io.IOException;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.demo.business.service.StudentService;
import ch.demo.business.service.StudentStatisticsService;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private StudentService studentService;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		
		StudentStatisticsService statistics = (StudentStatisticsService) session.getAttribute(
				StudentStatisticsService.class.toString());

		if (statistics == null) {
			
			response.getOutputStream().println("Retrieve new SFSB instance");
			
			try {
				InitialContext ctx = new InitialContext();
				statistics = (StudentStatisticsService) ctx
						.lookup("java:global/jee6-demo-ear-1.0.0-SNAPSHOT/jee6-demo-ejb-1.0.0-SNAPSHOT/StudentStatisticsServiceImpl");
				session.setAttribute(StudentStatisticsService.class.toString(), statistics);
			} catch (NamingException e) {
				throw new RuntimeException(e);
			}

		}
		statistics.hit();

		response.getOutputStream().println("Session id : " + request.getSession().getId());
		response.getOutputStream()
				.println("There are #" + studentService.getAll().size() + " students in the database");
		response.getOutputStream().println(statistics.getStatistics());
		response.getOutputStream().println(studentService.getStatistics());
	}

	@Override
	protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		this.doGet(arg0, arg1);
	}

}
