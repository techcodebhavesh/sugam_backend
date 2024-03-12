package com.ankush.test.Utils.Database;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

public class Table {
    @Autowired
    private @Getter DatabaseService database;
    private @Getter String name;

    public Table(DatabaseService database, String name) {
        this.database = database;
        this.name = name;
    }

}
