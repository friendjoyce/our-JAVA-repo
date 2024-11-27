package sqlOperator;

import sshWeb.SSHService;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import static security.CryptoUtil.decrypt;



public class Manager {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement ps = null;

    public Manager() {
        try {







            Driver driver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
            InputStream is = SSHService.class.getClassLoader().getResourceAsStream("SSH.properties");
            Properties prop = new Properties();
            try {
                prop.load(is);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String url = "jdbc:mysql://localhost:3307/javapro?useSSL=false&allowPublicKeyRetrieval=true";
            String user = "root";
            String password = null;
            try {
                password = decrypt(prop.getProperty("password"),prop.getProperty("key") );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn);


            stmt = conn.createStatement();


        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    protected void finalize() throws Throwable {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    public void excu(String sql) {
        try {
            //返回值是 影响数据库中的记录条数
            int a = stmt.executeUpdate(sql);
            System.out.println(a);
            System.out.println(a == 1 ? "成功" : "失败");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean userfind(String name, String password) throws SQLException {

        String sql = "select * from user where username=? and password=?;";
        ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, password);
        rs = ps.executeQuery();
        if(rs.next()) {
            return true;
        }
        return false;


    }
    public boolean userfind(String username) throws SQLException {

        String sql = "select * from user where username=?;";
        ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        rs = ps.executeQuery();
        if(rs.next()) {
            return true;
        }
        return false;


    }

    public void userinsert(String name, String password, int score) throws SQLException {

        String sql = "insert into user values(?,?,?);";
        ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, password);
        ps.setString(3, score + "");
        ps.executeUpdate();
    }

    public int userUpdateScore(String username, int score) throws SQLException {
        String sql = "select * from user where username=?;";
        ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        rs = ps.executeQuery();
        if(rs.next()) {
            int oldScore = rs.getInt("score");
            if(oldScore < score) {
                String sql2 = "update user set score=? where username=?;";
                ps = conn.prepareStatement(sql2);
                ps.setInt(1, score);
                ps.setString(2, username);
                int n = ps.executeUpdate();
                if(n > 0) {
                    System.out.println("success");
                    return 1;
                }
                System.out.println("unexcepted failure");
                return 2;
            }else{
                return 3;
            }
        }
        System.out.println("unexcepted failure");
        return 4;


    }


}


