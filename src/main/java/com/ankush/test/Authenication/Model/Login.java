package com.ankush.test.Authenication.Model;

import com.ankush.test.Utils.Database.DatabaseService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
public class Login {
    String userID;
    String sessionID;
    String status;
    @Autowired
    DatabaseService service;

    public Login(String userID, String sessionID, String status) {
        this.userID = userID;
        this.sessionID = sessionID;
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("[%s\t%s\t%s]", userID, sessionID, status);
    }
}
