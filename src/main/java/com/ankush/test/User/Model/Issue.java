package com.ankush.test.User.Model;

import com.ankush.test.Utils.Database.DatabaseService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Getter
public class Issue {
    @Autowired
    DatabaseService service;


    String ID;
    String userID;
    String location;
    String severity;
    long time;
    String status;
    String statusID;
    double coordX;
    double coordY;
    List<String> list;

    int insert() throws SQLException {
        PreparedStatement statement =
                service.getPreparedStatement("INSERT INTO Data (ID,location, user_id, severity, timestamp, status, status_id, coord_x, coord_y) VALUES(?,?, ?, ?, ?, ?, ?, ?, ?)");
        int count = 1;
        statement.setString(count++, ID);
        statement.setString(count++, location);
        statement.setString(count++, userID);
        statement.setString(count++, severity);
        statement.setLong(count++, time);
        statement.setString(count++, status);
        statement.setString(count++, statusID);
        statement.setDouble(count++, coordX);
        statement.setDouble(count++, coordY);

        for (String x : list) {
            service.executeUpdate("insert into Wastes (issue_id,type) values (?,?)", ID, x);
        }

        return statement.executeUpdate();
    }

    public Issue(ResultSet rs) throws SQLException {
        this.ID = rs.getString("id");
        this.location = rs.getString("location");
        this.userID = rs.getString("user_id");
        this.severity = rs.getString("severity");
        this.time = rs.getLong("timestamp");
        this.status = rs.getString("status");
        this.statusID = rs.getString("status_id");
        this.coordX = rs.getDouble("coord_x");
        this.coordY = rs.getDouble("coord_y");

        PreparedStatement stmt = service.getPreparedStatement("select * from Wastes where issue_id=?");
        stmt.setString(1, ID);
        service.getStringList(stmt.executeQuery(), "issue_id");
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Issue{");
        sb.append("service=").append(service);
        sb.append(", ID='").append(ID).append('\'');
        sb.append(", userID='").append(userID).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append(", severity='").append(severity).append('\'');
        sb.append(", time=").append(time);
        sb.append(", status='").append(status).append('\'');
        sb.append(", statusID='").append(statusID).append('\'');
        sb.append(", coordX=").append(coordX);
        sb.append(", coordY=").append(coordY);
        sb.append('}');
        return sb.toString();
    }
}
