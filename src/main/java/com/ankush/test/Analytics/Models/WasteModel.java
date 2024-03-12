package com.ankush.test.Analytics.Models;

import lombok.Getter;

import java.util.Map;

@Getter
public class WasteModel {
    String type;
    long count;

    public WasteModel() {
    }

    public WasteModel(String type, long count) {
        this.type = type;
        this.count = count;
    }
}
