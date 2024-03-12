package com.ankush.test.Analytics;

import com.ankush.test.Analytics.Models.WasteModel;
import com.ankush.test.Analytics.Radial.Model.AverageModel;
import com.ankush.test.Analytics.Radial.Model.SeverityModel;
import com.ankush.test.Analytics.Radial.Model.StatusModel;
import com.ankush.test.Analytics.Radial.Model.TimeModel;
import com.ankush.test.Utils.Database.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@RestController
public class AnalyticsAPI {

    @Autowired
    DatabaseService service;

    @GetMapping("/AISSMS/analytics/radialStatusLocation")
    public StatusModel getRadialStatusAnalytics(@RequestParam String location) {
        try {
            ResultSet rs = service.executeQuery("select status,count(status) as count from Data where location =? group by status", location);
            return new StatusModel(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/AISSMS/analytics/radialStatusCity")
    public StatusModel getRadialStatusAnalytics() {
        try {
            ResultSet rs = service.executeQuery("select status,count(status) as count from Data group by status");
            return new StatusModel(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/AISSMS/analytics/average")
    public AverageModel getAverageStatistics(@RequestParam String location) {
        try {
            ResultSet rs = service.executeQuery("select AVG(resolved_time -timestamp) as count from Data where status = \"COMPLETE\" and location=?", location);
            if (rs.next()) {
                long l = (long) rs.getDouble("count");
                System.out.println(new Timestamp(l));
                return new AverageModel(location, l / (1000 * 60 * 60));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    


    @GetMapping("/AISSMS/analytics/radialSeverity")
    public Map<String, SeverityModel> getRadialSeverityAnalytics(@RequestParam String location) {
        try {
            ResultSet rs = service.executeQuery("select severity,location,count(severity) as count from Data where location =? group by severity", location);
            Map<String, SeverityModel> map = new HashMap<>();
            while (rs.next()) {
                String loc = rs.getString("location");
                String severity = rs.getString("severity");
                int count = rs.getInt("count");
                if (severity != null) {
                    map.put(severity, new SeverityModel(severity, count));
                }
            }
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @GetMapping("/AISSMS/analytics/wasteType")
    public Map<String, WasteModel> getWasteTypeAnalytics(@RequestParam String location) {
        try {
            ResultSet rs = service.executeQuery("select location,type ,count(type) as count from Wastes where location=? group by type;", location);
            Map<String, WasteModel> map = new HashMap<>();
            while (rs.next()) {
                String loc = rs.getString("location");
                String type = rs.getString("type");
                Integer count = rs.getInt("count");
                if (type != null)
                    map.put(type, new WasteModel(type, count));
            }
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/AISSMS/analytics/totalComplaints")
    public Map<String, WasteModel> getComplaintStatistics(@RequestParam String location) {
        try {
            ResultSet rs = service.executeQuery("select * from Data where location=?", location);
            Map<String, WasteModel> map = new HashMap<>();
            while (rs.next()) {
                String loc = rs.getString("location");
                String type = rs.getString("type");
                Integer count = rs.getInt("count");
                if (type != null)
                    map.put(type, new WasteModel(type, count));
            }
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/AISSMS/analytics/timestamp")
    public List<TimeModel> getTimestamp(@RequestParam String location) {
        List<TimeModel> list = new ArrayList<>();
        long mult=1000*60*60*24;
        try {
            ResultSet rs = service.executeQuery(
                    "select counter,count(counter) as cases,status  from" +
                            " (select `timestamp`,location,status,round(`timestamp`/("+mult+")) as counter from Data where location=? order by `counter` )" +
                            " as data group by counter,status order by counter;\n", location);
            Map<Long, TimeModel> map = new LinkedHashMap<>();
            while (rs.next()) {
                long l = rs.getLong("counter");
                long timestamp=l*mult;
                long count = rs.getLong("cases");
                String status = rs.getString("status");
                TimeModel model = new TimeModel(l,timestamp);
                model.set(status, count);
                if (map.containsKey(l)) {
                    map.get(l).set(status, count);
                } else {
                    map.put(l, model);
                }
            }
            for (Map.Entry<Long, TimeModel> entry : map.entrySet()) {
                list.add(entry.getValue());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


}
