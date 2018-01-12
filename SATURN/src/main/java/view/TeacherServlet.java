package view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.DatabaseController;
import model.Availability;
import model.Career;
import model.User;

@Path("/teachers")
public class TeacherServlet {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public static List<User> getTeachers() throws SQLException, ClassNotFoundException {
		/*
		DatabaseController d = new DatabaseController();
		List<User> l = d.getUserbyType(User.TYPE.TEACHER.ordinal());
		System.out.println(User.TYPE.TEACHER.ordinal());
		*/
		DatabaseController d = new DatabaseController();
                
		ArrayList<User> l = d.getUserbyType(3, 1);//3 profesor, 1 carrera compu
                
		return l;
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public static User getTeacher(@PathParam("id") String idStr) {
		User c =  new User(0, "jyock1997@gmail.com", null, "Jose Paulo", "Yock Fuentes", 0, 0);
		return c;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTeacher(User usr) {
		String status;
		JSONObject object;		
		DatabaseController d;
		
		
		try {
                    
			d = new DatabaseController();
			if (d.insertNewUser(usr))
				status = "OK";
			else
				status = "ALREADY_EXISTS";
		} catch (ClassNotFoundException | SQLException e1) {
			return Response.status(500).entity(e1.toString()).build();
		}
		
		object = new JSONObject();
		try {
			object.put("status", status);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(object.toString()).build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateTeacher(@PathParam("id") String idStr, User usr) throws SQLException, ClassNotFoundException {
		//System.out.println(idStr);
		
		String status;
		JSONObject object;
		
		//System.out.println(idStr);
		status = "WRONG";
		
		object = new JSONObject();
		try {
                    DatabaseController d = new DatabaseController();
                    if(d.updateUser(usr, Integer.parseInt(idStr))){
                        status = "OK";
                        
                    }
			object.put("status", status);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(object.toString()).build();
	}

	@DELETE
    @Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTeacher(@PathParam("id") String idStr) throws SQLException, ClassNotFoundException {
		String status;
		JSONObject object;
		status = "WRONG";
		
		object = new JSONObject();
		try {
                    DatabaseController d = new DatabaseController();
                    if(d.deleteUser(Integer.parseInt(idStr))){
                        status = "OK";
                        
                    }
			object.put("status", status);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(object.toString()).build();
	}

	@GET
	@Path("/availabilities/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public static List<Availability> getTeacherAvailabilities(@PathParam("id") String idStr) throws SQLException, ClassNotFoundException {
		//DatabaseController d = new DatabaseController();
		//List<Career> l = d.();
		ArrayList<Availability> l = new ArrayList<>();
		l.add(new Availability(0, Availability.DAY.MONDAY.ordinal(), 9, 19));
		l.add(new Availability(0, Availability.DAY.TUESDAY.ordinal(), 7, 21));
		l.add(new Availability(0, Availability.DAY.WEDNESDAY.ordinal(), 9, 10));
		l.add(new Availability(0, Availability.DAY.WEDNESDAY.ordinal(), 12, 12));
		l.add(new Availability(0, Availability.DAY.THURSDAY.ordinal(), 9, 19));
		l.add(new Availability(0, Availability.DAY.FRIDAY.ordinal(), 9, 19));
		l.add(new Availability(0, Availability.DAY.SATURDAY.ordinal(), 9, 19));
		
		return l;
	}
	
	@PUT
	@Path("/availabilities/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateTeacherAvailabilities(@PathParam("id") String idStr, List<Availability> l) {
		System.out.println(idStr);
		ArrayList<Availability> list = (ArrayList<Availability>)l;
		for(int i = 0; i< list.size(); i++) {
			System.out.println(list.get(i).getDay() + " " + list.get(i).getStartHour() + " " + list.get(i).getEndHour());
		}
		String status;
		JSONObject object;
		
		status = "OK";
		
		object = new JSONObject();
		try {
			object.put("status", status);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(object.toString()).build();
	}
}
