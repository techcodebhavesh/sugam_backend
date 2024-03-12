package com.ankush.test.Analytics.Radial.Model;

import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
public class StatusModel {
    int complete;
    int pending;
    int ongoing;
    private static final String COMPLETE="complete",ONGOING="ongoing",PENDING="pending";

    public StatusModel(ResultSet rs) throws SQLException {
        while(rs.next()){
            String s=rs.getString("status");
            if(s.equalsIgnoreCase(COMPLETE)){
                complete=rs.getInt("count");
            }
            if(s.equalsIgnoreCase(ONGOING)){
                ongoing=rs.getInt("count");
            }
            if(s.equalsIgnoreCase(PENDING)){
                pending=rs.getInt("count");
            }
        }
    }
}

