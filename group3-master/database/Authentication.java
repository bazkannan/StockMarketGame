package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authentication {
	
public static int serverAuthenticateUser(String username , String password) {

        try {
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection("jdbc:postgresql://mod-msc-sw1.cs.bham.ac.uk/", "group3", "56wh3nujfw");

			String hashedPassword = database.Hashing.sha256(password);

			String sql = "SELECT * from users WHERE username='" + username + "' and password='" + hashedPassword + "'";
			
			PreparedStatement prepStmt = connection.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();

			// if the username and passwords match return 1
			if(rs.next()){
				return 1;

		    } else {
		        return 0;
		    }
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        return 0;

    }
}
