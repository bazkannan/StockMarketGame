package database;


import java.sql.*;

public class Signup {

    public static int Signup(String username , String password, String email) {


        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://mod-msc-sw1.cs.bham.ac.uk/", "group3", "56wh3nujfw");

            Statement statementSQL = connection.createStatement();

            String signUpSQL = "SELECT * from users WHERE username='" + username + "'";

            PreparedStatement prepStmt = connection.prepareStatement(signUpSQL);
            ResultSet rs = prepStmt.executeQuery();

            // if the username doesn't already exist, create account
            if(!rs.next()){

                String hashedPassword = database.Hashing.sha256(password);

                statementSQL.executeUpdate("INSERT INTO users (username,password,email_address) " +
                        "VALUES ('"+username+"','"+hashedPassword+"','"+email+"')");
                System.out.println(" Username & Password registered!\n");
                System.out.println("Username registered: " + username);
                System.out.println("Password registered: " + hashedPassword);
                System.out.println("Email registered: " + email);
                connection.close();

                return 1;

            } else {
                System.out.println("Username already exists, please try again");
                connection.close();
                return 0;
            }


        } catch (SQLException e) {
            System.out.println("SQL Exception: "+ e.toString());
        } catch (ClassNotFoundException cE) {
            System.out.println("Class Not Found Exception: "+ cE.toString());
        }


        return 0;
    }

}
