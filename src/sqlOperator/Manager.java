package sqlOperator;

import entity.User;
import sshWeb.SSHService;
import timer.GameTimer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static security.CryptoUtil.decrypt;
import static security.PasswordEncryption.*;


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
                password = decrypt(prop.getProperty("password2"),prop.getProperty("key") );
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
        String sql1 = "select * from user where username=?;";

        ps = conn.prepareStatement(sql1);
        ps.setString(1, name);
        rs = ps.executeQuery();
        if(!rs.next()) {
            return false;
        }
        String salt =   rs.getString("salt");
        String hashedpassword = rs.getString("password");
        boolean flag = false;
        flag = verifyPassword(password,salt,hashedpassword);
        return flag;


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

    public boolean userinsert(String name, String password, int score) throws SQLException {
        if(name.isEmpty()&&password.isEmpty()) {
            return false;
        }
        String salt = generateSalt();
        String hashedpassword = hashPassword(password,salt);

        String sql = "insert into user values(?,?,?,?,?);";
        ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, hashedpassword);
        ps.setString(3, score + "");
        ps.setString(4, salt);
        ps.setString(5,"never win");
        ps.executeUpdate();
        return true;
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

    public int userUpdateScore(String username, int score, String time) throws SQLException {
        String sql = "select * from user where username=?;";
        ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        rs = ps.executeQuery();
        if(rs.next()) {
            String oldTime = rs.getString("time");
            int oldScore = rs.getInt("score");

            if (oldTime.equals("never win")||comparetime(oldTime, time) > 0) {
                    String sql3 = "update user set time=? where username=?;";
                    ps = conn.prepareStatement(sql3);
                    ps.setString(1, time);
                    ps.setString(2, username);
                    int n1 = ps.executeUpdate();


            }
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

    public int comparetime(String time1, String time2) {
        // 解析时间字符串
        String[] parts1 = time1.split(":");
        String[] parts2 = time2.split(":");

        int minutes1 = Integer.parseInt(parts1[0]);
        int seconds1 = Integer.parseInt(parts1[1]);

        int minutes2 = Integer.parseInt(parts2[0]);
        int seconds2 = Integer.parseInt(parts2[1]);

        // 转换为总秒数
        int totalSeconds1 = minutes1 * 60 + seconds1;
        int totalSeconds2 = minutes2 * 60 + seconds2;

        // 比较总秒数
        return Integer.compare(totalSeconds1, totalSeconds2);
    }



    public List<User> getTop5UsersByScore() throws SQLException {
        List<User> topUsers = new ArrayList<>();
        String sql = "SELECT username, score, time FROM user ORDER BY score DESC LIMIT 5;";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            String username = rs.getString("username");
            int score = rs.getInt("score");
            String time = rs.getString("time");
            topUsers.add(new User(username, score,time));
        }
        return topUsers;
    }



}


