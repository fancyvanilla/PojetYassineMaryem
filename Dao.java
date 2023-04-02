import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class userDao {
    String dbPassword = "secret"; //your user password
    String dbName = "root";   // your user name its par in default root
    String url = "jdbc:mysql://localhost:3306/app"; // change "app" with your database name
    private Connection con;
  
    userDao()
    {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); //you need to have jdbc istalled and place the jar file in the lib folder in the project
            con=DriverManager.getConnection(url,dbName,dbPassword);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }


    public int Login(String name,String password) throws SQLException {
        String sql="select * from users where name=? and password=?";
        PreparedStatement stmt=con.prepareStatement(sql);
        stmt.setString(1,name);
        stmt.setString(2,password);
        ResultSet res=stmt.executeQuery();
        con.close();
        if (res.next())
            return res.getInt("id");
        return 0 ;
    }

};
