package com.ankush.test;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

@Component("PropertyService")
public class PropertyManager {
    String DATABASE_USERNAME;
    String DATABASE_PASSWORD;
    String DATABASE_URL;
    static String PROPERTIES_FILE = "res/properties.properties";
    Properties properties = new Properties();

    public PropertyManager() {
        try {
            properties.load(new FileInputStream(PROPERTIES_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        DATABASE_USERNAME = properties.getProperty("database.username");
        DATABASE_PASSWORD = properties.getProperty("database.password");
        DATABASE_URL = properties.getProperty("database.password");
    }

    public static Optional<Properties> getProperties() {
        try {
            Properties p = new Properties();
            p.load(new FileInputStream(PROPERTIES_FILE));
            return Optional.of(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
