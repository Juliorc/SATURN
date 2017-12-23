/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Course;

/**
 *
 * @author julio
 */
public class CourseConnector {
    public Boolean insertNewCourse(Connection conn, Course c) throws ClassNotFoundException{
        boolean state = false;
        try{
        Class.forName("com.mysql.jdbc.Driver");

        String sql = "INSERT INTO Course(CourseCode, CourseName, Semester, CareerName, Plan) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, c.getCode());
        statement.setString(2, c.getName());
        statement.setInt(3, c.getSemester());
        statement.setString(4, c.getCareer());
        statement.setString(5, c.getPlan());

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new Course was inserted successfully!");
            state = true;
        }

        } catch(SQLException ex) {
         ex.printStackTrace();
        }
        return state;
    }
    
    public ResultSet getCoursebyCareer(Connection conn, String Career, String Plan) throws ClassNotFoundException{
        ResultSet rs = null;
        try{    
            Class.forName("com.mysql.jdbc.Driver");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Course where CareerName = ? and Plan = ?");
            stmt.setString(1, Career);
            stmt.setString(2, Plan);
            rs = stmt.executeQuery();
            
        } catch (SQLException e){
            e.printStackTrace();
        }
        return rs;
    }
    
    public ResultSet getCoursebyId(Connection conn, int id) throws ClassNotFoundException{
        ResultSet rs = null;
        try{    
            Class.forName("com.mysql.jdbc.Driver");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Course where CourseId = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
        } catch (SQLException e){
            e.printStackTrace();
        }
        return rs;
    }
}