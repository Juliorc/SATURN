package databaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Availability;

public class AvailabilityConnector {
    public Boolean insertNewAvailability(Connection conn, Availability a) throws ClassNotFoundException{
        boolean state = false;
        try{
        Class.forName("com.mysql.jdbc.Driver");

        String sql = "INSERT INTO Availabilities(ProfessorId, StartHour, EndHour, Day) VALUES (?, ?, ?,?)";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, a.getTeacher());
        statement.setInt(2, a.getStartHour());
        statement.setInt(3, a.getEndHour());
        statement.setInt(4, a.getDay());

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new Availability was inserted successfully!");
            state = true;
        }

        } catch(SQLException ex) {
         ex.printStackTrace();
        }
        return state;
    }
    
    public ResultSet getAvailabilitybyProfessor(Connection conn, int professor) throws ClassNotFoundException{
        ResultSet rs = null;
        try{    
            Class.forName("com.mysql.jdbc.Driver");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Availabilities where ProfessorId = ?");
            stmt.setInt(1, professor);
            rs = stmt.executeQuery();
            
        } catch (SQLException e){
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getAvailability(Connection conn) throws ClassNotFoundException {
        ResultSet rs = null;
        try{    
            Class.forName("com.mysql.jdbc.Driver");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Availabilities");
            rs = stmt.executeQuery();
            
        } catch (SQLException e){
            e.printStackTrace();
        }
        return rs;
    }
}
