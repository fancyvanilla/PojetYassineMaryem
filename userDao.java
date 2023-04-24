import java.sql.*;
import java.util.*;
import java.util.Date;
import org.mindrot.jbcrypt.BCrypt;
import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

public class userDao {
    String dbPassword = "secret";
    String dbName = "root";
    String url = "jdbc:mysql://localhost:3306/app";
    private Connection con;
    userDao()
    {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection(url,dbName,dbPassword);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
   public String Signup(String name,String password,Boolean role) throws SQLException {
        if (!isValidPassword(password)) return 1;
        if (login(name,password)==null)
        {
            String sql="insert into users(name,password,role) values(?,?,?)";
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            PreparedStatement stmt2=con.prepareStatement(sql);
            stmt2.setString(1,name);
            stmt2.setString(2,hashedPassword);
            stmt2.setBoolean(3,role);
            stmt2.execute();
            return 0;
        }
        return -1;
    }
   public User login(String name,String password) throws SQLException {
        String sql="select * from users where name=?";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        PreparedStatement stmt=con.prepareStatement(sql);
        stmt.setString(1,name);
        ResultSet res=stmt.executeQuery();
        if (res.next() && BCrypt.checkpw(password, res.getString(password)))
        {
            User user=new User();
            user.setName(name);
            user.setId(res.getInt("id"));
            user.setPassword(res.getString("password"));
            user.setRole(res.getInt("role"));
            return user;
        }
        return null;
    }
    private Boolean isValidPassword(String password) {
        if (password.length() < 8) return false;
        boolean containLetters = false;
        boolean containDigits = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (isLetter(c)) containLetters = true;
            if (isDigit(c)) containDigits = true;
        }
        return (containDigits && containLetters);
    }
    public ArrayList<User> getUserByName(String name) throws SQLException {
        ArrayList<User> users=new ArrayList<>();
        String sql="select * from users where name=?";
        PreparedStatement st=con.prepareStatement(sql);
        st.setString(1,name);
        ResultSet res= st.executeQuery();
        while(res.next())
        {
            User user=new User();
            user.setName(name);
            user.setId(res.getInt("id"));
            user.setPassword(res.getString("password"));
            users.add(user);
        }
        return users;
    }

    public int calcScore(int id) throws SQLException {
        String sql="select * from course_student a join courses b on a.course_id=b.id where id_student=? and state=1";
        PreparedStatement stmt=con.prepareStatement(sql);
        stmt.setInt(1,id);
        ResultSet res=stmt.executeQuery();
        int score=0;
        while(res.next())
        {
            score+=res.getInt("points");
        }
        stmt.close();
        res.close();
        return score;
    }
    public Course enroll(int user_id,String title,Date date) throws SQLException {
        Course course=this.getCourseByTitle(title); //we suppose titles are unique
        assert course != null;
        if (date.compareTo(course.getStart())>0 && date.compareTo(course.getDeadline())<0 && !this.isEnrolled(user_id,course.getId())){
            String sql = "insert into course_student(id_student,id_course)values(?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, user_id);
            stmt.setInt(2, course.getId());
            stmt.execute();
            stmt.close();
            return course;
        }
        return null;
    }

    private Course getCourseByTitle(String title) throws SQLException {
        String sql="select * from courses where title=?";
        PreparedStatement stmt=con.prepareStatement(sql);
        stmt.setString(1,title);
        ResultSet res= stmt.executeQuery();
        if (res.next())
        {
            Course course=new Course();
            course.setTitle(title);
            course.setId(res.getInt("id"));
            course.setTeachId(res.getInt("id_teach"));
            //and others
            return course;
        }
        return null;
    }

    private Boolean isEnrolled(int id_course,int id_user) throws SQLException {
        String sql="select * from course_student where id_course=? and id_student=?";
        PreparedStatement stmt=con.prepareStatement(sql);
        stmt.setInt(1,id_course);
        stmt.setInt(2,id_user);
        ResultSet res= stmt.executeQuery();
        return !res.next() ;
    }
    public ArrayList<Course>getUserCourses(int id_user) throws SQLException {
        String sql="select * from courses a join course_student b on a.id=b.course_id and a.student_id=? ";
        PreparedStatement stmt=con.prepareStatement(sql);
        stmt.setInt(1,id_user);
        ResultSet res=stmt.executeQuery();
        ArrayList<Course> arr=new ArrayList<>();
        while (res.next()) {
            Course course = new Course();
            course.setId(res.getInt("id"));
            course.setTitle(res.getString("title"));
            course.setBody(res.getString("body"));
            course.setTeachId(res.getInt("teach_id"));
            course.setCategory(res.getInt("category"));
            course.setStart(res.getDate("start"));
            course.setDeadline(res.getDate("deadline"));
            course.setCapacity(res.getInt("capacity"));
            course.setHomework(res.getString("homework"));
            course.setPoints(res.getInt("points"));
            arr.add(course);
        }
        return arr;
    }
    public ArrayList<Course> getCourses() throws SQLException {
        String sql = "SELECT * FROM courses";
        return executeQuery(sql);
    }
    public ArrayList<Course> getCoursesByCat(String cat) throws SQLException {
        String sql = "SELECT * FROM courses";
        if (!cat.equals("")) {
            sql += " WHERE category = ?";
            return executeQuery(sql, cat);
        }
        return executeQuery(sql);
    }
    private ArrayList<Course> executeQuery(String sql, String... params) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            stmt.setString(i + 1, params[i]);
        }
        ResultSet res = stmt.executeQuery();
        ArrayList<Course> arr = new ArrayList<>();
        while (res.next()) {
            Course course = new Course();
            course.setId(res.getInt("id"));
            course.setTitle(res.getString("title"));
            course.setBody(res.getString("body"));
            course.setTeachId(res.getInt("teach_id"));
            course.setCategory(res.getInt("category"));
            course.setStart(res.getDate("start"));
            course.setDeadline(res.getDate("deadline"));
            course.setCapacity(res.getInt("capacity"));
            course.setHomework(res.getString("homework"));
            course.setPoints(res.getInt("points"));
            arr.add(course);
        }
        return arr;
    }
    public Map<Integer,Integer> getRanking(Integer id) throws SQLException {
        String sql = "{CALL getRanking(?);}"; //procedure stockée ta zahi
        CallableStatement stmt = con.prepareCall(sql);
        stmt.setInt(1,id);
        ResultSet res= stmt.executeQuery();
        Map<Integer, Integer> map = new LinkedHashMap<>();
        while(res.next())
            map.put(res.getInt("student_id"),res.getInt("score"));
        return map;
    }
    public void closeConnection() throws SQLException {
        con.close();
    }
}

