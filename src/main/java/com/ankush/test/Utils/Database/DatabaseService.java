package com.ankush.test.Utils.Database;


import com.ankush.test.Authenication.Model.Login;
import com.ankush.test.PropertyManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Component("DatabaseService")
public class DatabaseService {

    @Autowired
    PropertyManager properties;

    public String URL = "jdbc:mysql://localhost:3306/AISSMS";
    public String user = "root";
    public String password = "Password@123";
    private static final String driverClass = "com.mysql.cj.jdbc.Driver";

    private @Getter Connection con = null;

    public DatabaseService() {
        Optional<Properties> prop = PropertyManager.getProperties();
        if (prop.isPresent()) {
            user = prop.get().getProperty("database.username");
            password = prop.get().getProperty("database.password");
            URL = prop.get().getProperty("database.url");
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            con = DriverManager.getConnection(URL, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        user=properties.getDATABASE_USERNAME();
//        password= properties.getDATABASE_PASSWORD();


    }

    public DatabaseService(String URL, String user, String password) throws SQLException {
        this.URL = URL;
        this.user = user;
        this.password = password;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        con = DriverManager.getConnection(URL, user, password);

    }

    public PreparedStatement getPreparedStatement(String s) throws SQLException {
        return con.prepareStatement(s);
    }

    public Statement getStatement() throws SQLException {
        return con.createStatement();
    }

    public ResultSet executeQuery(String query, Object... args) throws SQLException {
        PreparedStatement ps = getPreparedStatement(query);
        for (int i = 0; i < args.length; i++) {
            ps.setString(i + 1, args[i].toString());
        }
        return ps.executeQuery();
    }

    public int executeUpdate(String query, Object... args) throws SQLException {
        PreparedStatement ps = getPreparedStatement(query);
        for (int i = 0; i < args.length; i++) {
            ps.setString(i + 1, args[i].toString());
        }
        return ps.executeUpdate();
    }

    public List<String> getStringList(ResultSet rs, String col) {
        List<String> list = new ArrayList<>();
        try {
            while (rs.next()) {
                list.add(rs.getString(col));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
