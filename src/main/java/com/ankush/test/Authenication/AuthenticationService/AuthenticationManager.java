package com.ankush.test.Authenication.AuthenticationService;

import com.ankush.test.Authenication.Model.Login;
import com.ankush.test.Utils.Database.DatabaseService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;


@Component("AuthenicationManager")
public class AuthenticationManager {
    @Autowired
    DatabaseService service;

    public AuthenticationManager() {
    }

    public AuthenticationManager(DatabaseService service) {
        this.service = service;
    }

    public Optional<Login> getLoginFromID(String userId) {
        throw new UnsupportedOperationException();
    }

    public Optional<Login> getLoginFromSession(String userId) {
        if (userId == null) {
            return Optional.empty();
        }
        try {
            ResultSet rs = service.executeQuery("select Login.user_id,Login.username,Login.status from Login,Sessions where Sessions.session_id =?", userId);
            if (rs.next())
                return Optional.of(new Login(rs.getString("Login.user_id"), rs.getString("Login.username"), rs.getString("Login.status")));
        } catch (SQLException e) {
            System.err.println(e.toString());
        }
        return Optional.empty();
    }

    public boolean authenticate(String userID, String password) {
        try {
            ResultSet rs = service.executeQuery("select * from Login where user_id=? and password=?", userID, password);
            if(rs.next()){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean addLogin(String userID, String username, String status, String password) {
        try {
            service.executeUpdate("insert into Login (user_id,username,password,status) values (?,?,?,?)", userID, username, password, status);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String registerSession(HttpServletResponse response, String userID) {

        String uuid = UUID.randomUUID().toString();
        try {
            service.executeUpdate("insert into Sessions (user_id,session_id) values(?,?)", userID, uuid);
            Cookie cookie = new Cookie("AUTHENTICATION_ID", uuid);
            cookie.setPath("/AISSMS");
//            cookie.setSecure(true);
            response.addCookie(cookie);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return uuid;
    }


    public Optional<Login> CheckAuthenticatedFromCookie(HttpServletRequest req) {
        Cookie c = WebUtils.getCookie(req, "AUTHENTICATION_ID");
        if (c == null) {
            return Optional.empty();
        }
        return getLoginFromSession(c.getValue());
    }


}
