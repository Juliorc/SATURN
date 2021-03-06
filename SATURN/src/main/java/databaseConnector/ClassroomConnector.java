package databaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Classroom;
import model.ClassroomType;

public class ClassroomConnector {
    
    public ResultSet getClassrooms(Connection conn) throws ClassNotFoundException {
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            PreparedStatement stmt = conn.prepareStatement( "SELECT Classrooms.ClassroomId, Classrooms.Name, Classrooms.Capacity, ClassroomTypes.ClassroomType, ClassroomTypes.Name as ClassroomTypeName " +
                                                            "FROM Classrooms, ClassroomTypes " +
                                                            "WHERE Classrooms.ClassroomType = ClassroomTypes.ClassroomType;");
            rs = stmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public ResultSet getClassroom(Connection conn, int id) throws ClassNotFoundException {
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            PreparedStatement stmt = conn.prepareStatement( "SELECT ClassroomId, Classrooms.Name, Capacity, ClassroomTypes.ClassroomType, ClassroomTypes.Name as ClassroomTypeName " +
                                                            "FROM Classrooms, ClassroomTypes " +
                                                            "WHERE Classrooms.ClassroomType = ClassroomTypes.ClassroomType and ClassroomId = ?;");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public Boolean insertNewClassroom(Connection conn, Classroom c) throws ClassNotFoundException {
        boolean state = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            PreparedStatement statement = conn.prepareStatement("INSERT INTO Classrooms (Name, Capacity, ClassroomType) VALUES (?, ?, ?);");
            statement.setString(1, c.getName());
            statement.setInt(2, c.getCapacity());
            statement.setInt(3, c.getClassroomType());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new Classroom was created successfully!");
                state = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return state;
    }
    
    public Boolean updateClassroom(Connection conn, int id, Classroom classroom) throws ClassNotFoundException {

        boolean state =  false;
        int cont = 1;
        boolean first = false;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String sql = "UPDATE Classrooms SET ";
            if(classroom.getName() != null){
                sql += " Name = ?";
                first = true;
            }
            if(classroom.getCapacity() != 0){
                if(first)
                    sql += ",";
                sql += " Capacity = ?";
                first = true;
            }
            if(classroom.getClassroomType() != 0){
                if(first)
                    sql += ","; 
                sql += " ClassroomType = ?";
                first = false;
            }
            sql += "  WHERE ClassroomId = ?;";
            
            PreparedStatement statement = conn.prepareStatement(sql);
            if(classroom.getName() != null)
                statement.setString(cont++, classroom.getName());
            if(classroom.getCapacity() != 0)
                statement.setInt(cont++, classroom.getCapacity());
            if(classroom.getClassroomType() != 0)
                statement.setInt(cont++, classroom.getClassroomType());
            
            statement.setInt(cont++, id);
            
            System.out.println(statement.toString());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Classroom was updated successfully!");
                state = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return state;
    }

    public Boolean deleteClassroom(Connection conn, int id) throws ClassNotFoundException {
        boolean state = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String sql = "DELETE FROM Classrooms WHERE ClassroomId = ?;";

            PreparedStatement statement = conn.prepareStatement(sql);
            
            statement.setInt(1, id);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Classroom was deleted successfully!");
                state = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return state;
    }

    public ResultSet getClassroombyType(Connection conn, int Type) throws ClassNotFoundException {
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Classrooms where ClassroomType = ?");
            stmt.setInt(1, Type);
            rs = stmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getClassroomCapacity(Connection conn) throws ClassNotFoundException {
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            PreparedStatement stmt = conn.prepareStatement("SELECT Capacity FROM Classrooms");
            rs = stmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public ResultSet getClassroomCapacity(Connection conn, int type) throws ClassNotFoundException{
            ResultSet rs = null;
            try{    
                Class.forName("com.mysql.jdbc.Driver");
                PreparedStatement stmt = conn.prepareStatement("SELECT Capacity FROM Classrooms where ClassroomType = ?");
                stmt.setInt(1, type);
                rs = stmt.executeQuery();

            } catch (SQLException e){
                e.printStackTrace();
            }
            return rs;
        }

    public int getClassroomQuantity(Connection conn, int type) throws ClassNotFoundException, SQLException {
        ResultSet rs = null;
        int result = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            PreparedStatement stmt = conn.prepareStatement("SELECT MAX(ClassroomId) FROM Classrooms where ClassroomType = ?");
            stmt.setInt(1, type);
            rs = stmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        while (rs.next()) {
            result = rs.getInt("MAX(ClassRoomId)");
        }
        return result;
    }
    
    public ResultSet getClassroomTypes(Connection conn) throws ClassNotFoundException {
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ClassroomTypes");
            rs = stmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public ResultSet getClassroomsType(Connection conn) throws ClassNotFoundException {
        ResultSet rs = null;
            try{    
                Class.forName("com.mysql.jdbc.Driver");
                PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT ClassroomType FROM Classrooms");
                rs = stmt.executeQuery();

            } catch (SQLException e){
                e.printStackTrace();
            }
            return rs;
    }
    
    public ResultSet getClassroomType(Connection conn, int id) throws ClassNotFoundException {
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ClassroomTypes WHERE ClassroomTypes.ClassroomType = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public Boolean insertNewClassroomType(Connection conn, ClassroomType classroomType) throws ClassNotFoundException {
        boolean state = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            PreparedStatement statement = conn.prepareStatement("INSERT INTO ClassroomTypes (Name, Description) VALUES (?, ?);");
            statement.setString(1, classroomType.getName());
            statement.setString(2, classroomType.getDescription());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new ClassroomType was inserted successfully!");
                state = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return state;
    }

    public Boolean updateClassroomType(Connection conn, int id, ClassroomType classroomType) throws ClassNotFoundException {
        
        
        boolean state =  false;
        int cont = 1;
        boolean first = false;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String sql = "UPDATE ClassroomTypes SET ";
            if(classroomType.getName() != null){
                sql += " Name = ?";
                first = true;
            }
            if(classroomType.getDescription() != null){
                if(first)
                    sql += ",";
                sql += " Description = ?";
                first = true;
            }
            sql += "  WHERE ClassroomType = ?;";
            
            PreparedStatement statement = conn.prepareStatement(sql);
            if(classroomType.getName() != null)
                statement.setString(cont++, classroomType.getName());
            if(classroomType.getDescription() != null)
                statement.setString(cont++, classroomType.getDescription());
            
            statement.setInt(cont++, id);

            System.out.println(statement.toString());
            
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("ClassroomType was updated successfully!");
                state = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return state;
    }

    public Boolean deleteClassroomType(Connection conn, int id) throws ClassNotFoundException {
        boolean state = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String sql = "DELETE FROM ClassroomTypes WHERE ClassroomType = ?;";

            PreparedStatement statement = conn.prepareStatement(sql);
            
            statement.setInt(1, id);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("ClassroomType was deleted successfully!");
                state = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return state;
    }
}
