package com.ankush.test.Analytics.Radial.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AverageModel {
    String location;
    long average;

    public AverageModel(String location, long average) {
        this.location = location;
        this.average = average;
    }
}
