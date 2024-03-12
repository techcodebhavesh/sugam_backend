package com.ankush.test.User.Controller;

import com.ankush.test.User.Model.Issue;
import com.ankush.test.Utils.Database.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class IssueController {
    @Autowired
    DatabaseService service;

    int setStatus(String status) {
        try {
            return service.executeUpdate("update Data set status=?", status);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    int setStatusID(String statusID) {
        try {
            return service.executeUpdate("update Data set status_id=?", statusID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Optional<Issue> getIssue(long id) {
        try {
            PreparedStatement stmt = service.getPreparedStatement("select * from Data where id=?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new Issue(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.empty();
    }
}
