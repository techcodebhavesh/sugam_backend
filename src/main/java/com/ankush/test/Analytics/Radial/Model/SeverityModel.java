package com.ankush.test.Analytics.Radial.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@NoArgsConstructor
public class SeverityModel {
    String severity;
    long count;

    public SeverityModel(String severity, long count) {
        this.severity = severity;
        this.count = count;
    }

}

