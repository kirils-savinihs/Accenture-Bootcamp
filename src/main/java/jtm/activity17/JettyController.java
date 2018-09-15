package jtm.activity17;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtm.activity13.Teacher;
import jtm.activity13.TeacherManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JettyController {

	TeacherManager manager;

	/**
	 * method which is invoked when root folder (i.e. http://localhost:8080/) of web
	 * application is requested. This method doesn't take any parameters passed in
	 * URL (address).
	 * 
	 * @return string as HTML response to the request using UTF-8 encoding for
	 *         non-Latin characters.
	 */
	@RequestMapping(value = "/", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	// This method should work without declared name parameter, request and
	// response objects,
	// but it shows, how passed request and returned response can be used inside
	// method
	public String homePage(@RequestParam(value = "name", required = false) String name, HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder sb = new StringBuilder();
		sb.append("<a href='/insertTeacher'>Insert teacher<a><br/>\n");
		sb.append("<a href='/findTeacher'>Find teacher<a><br/>\n");
		sb.append("<a href='/deleteTeacher'>Delete teacher<a><br/>\n");
		// Following is also redundant because status is OK by default:
		response.setStatus(HttpServletResponse.SC_OK);
		return sb.toString();
	}

	// TODO Implement insertTeacher() method

	@RequestMapping(value = "/insertTeacher", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String insertTeacher(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "surname", required = false) String surname, HttpServletRequest request, HttpServletResponse response) {
		manager = new TeacherManager();
		StringBuilder sb = new StringBuilder();
		if (name == null || surname == null) {
			response.setStatus(HttpServletResponse.SC_OK);
			sb.append("<form action=''>\n" + "Name: <input type='text' name='name' value=''><br/>\n"
					+ "Surname:<input type='text' name='surname' value=''><br/>\n" + "<input type='submit' value='Insert'></form><br/>\n"
					+ "<a href='/'>Back</a>\n");

		} else if (name.equals("") || surname.equals("")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			sb = new StringBuilder();
			sb.append("false<br/>\n" + "<a href='/'>Back</a>\n");
		} else if (manager.insertTeacher(name, surname)) {

			response.setStatus(HttpServletResponse.SC_OK);
			sb = new StringBuilder();
			sb.append("true<br/>\n" + "<a href='/'>Back</a>\n");

		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			sb.append("TeacherManager Insert method returned false");
		}

		return sb.toString();

	}

	// TODO Implement findTeacher() method
	@RequestMapping(value = "/findTeacher", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String findTeacher(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "surname", required = false) String surname, HttpServletRequest request, HttpServletResponse response) {
		manager = new TeacherManager();
		StringBuilder sb = new StringBuilder();
		if (name == null && surname == null) {
			response.setStatus(HttpServletResponse.SC_OK);
			sb.append("<form action=''>\n" + "Name: <input type='text' name='name' value=''><br/>\n"
					+ "Surname:<input type='text' name='surname' value=''><br/>\n" + "<input type='submit' value='Find'><br/>\n"
					+ "<a href='/'>Back</a>\n");
		} else {
			response.setStatus(HttpServletResponse.SC_OK);
			List<Teacher> result = manager.findTeacher(name, surname);
			sb.append("<form action=''>\n" + "Name: <input type='text' name='name' value=''><br/>\n"
					+ "Surname:<input type='text' name='surname' value=''><br/>\n" + "<input type='submit' value='Find'><br/>\n"
					+ "<table>\n");
			for (Teacher t : result) {
				sb.append("<tr>\n" + "<td>" + t.getID() + "</td>\n" + "<td>" + t.getFirstName() + "</td>\n" + "<td>" + t.getLastName()
						+ "</td>\n" + "</tr>\n");
			}
			sb.append("</table><br>\n" + "<a href='/'>Back</a>\n");

		}
		return sb.toString();
	}

	// TODO Implement deleteTeacher() method
	@RequestMapping(value = "/deleteTeacher", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String deleteTeacher(@RequestParam(value = "id", required = false) String id, HttpServletRequest request,
			HttpServletResponse response) {
		manager = new TeacherManager();
		StringBuilder sb = new StringBuilder();

		if (id == null) {
			response.setStatus(HttpServletResponse.SC_OK);
			sb.append("<form action=''>\n" + "ID:<input type='text' name='id' value=''><br/>\n"
					+ "<input type='submit' value='Delete'><br/>\n" + "<a href='/'>Back</a>\n");
		} else if (id.equals("")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			sb.append("false<br/>\n" + "<a href='/'>Back</a>\n");

		} else if (Integer.parseInt(id) > 0) {
			manager.deleteTeacher(Integer.parseInt(id));
			response.setStatus(HttpServletResponse.SC_OK);
			sb.append("true<br/>\n" + "<a href='/'>Back</a>\n");
		}

		return sb.toString();
	}
}
