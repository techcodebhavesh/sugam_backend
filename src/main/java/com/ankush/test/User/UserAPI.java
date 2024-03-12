package com.ankush.test.User;

import com.ankush.test.User.Model.Issue;
import com.ankush.test.Utils.Database.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserAPI {
    @Autowired
    DatabaseService service;

    @GetMapping("/AISSMS/user/neighbours")
    public List<Issue> getUserNeighbours(@RequestParam double x, @RequestParam double y) {
        try {
            PreparedStatement stmt = service.getPreparedStatement("select *,pow(coord_x-?,2)+pow(coord_y-?,2) as distance from Data order by distance");
            stmt.setDouble(1, x);
            stmt.setDouble(2, y);
            ResultSet rs = stmt.executeQuery();
            List<Issue> issueList = new ArrayList<>();
            while (rs.next()) {
                issueList.add(new Issue(rs));
            }
            return issueList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/AISSMS/user/get")
    public List<Issue> getUser(@RequestParam double x, @RequestParam double y) {
        try {
            PreparedStatement stmt = service.getPreparedStatement("select *,pow(coord_x-?,2)+pow(coord_y-?,2) as distance from Data order by distance");
            stmt.setDouble(1, x);
            stmt.setDouble(2, y);
            ResultSet rs = stmt.executeQuery();
            List<Issue> issueList = new ArrayList<>();
            while (rs.next()) {
                issueList.add(new Issue(rs));
            }
            return issueList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
