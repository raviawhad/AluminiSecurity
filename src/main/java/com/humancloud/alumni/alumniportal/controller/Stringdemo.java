package com.humancloud.alumni.alumniportal.controller;

import java.util.ArrayList;
import java.util.List;

public class Stringdemo {

  public static int add(String numbers) {
    if (numbers.isEmpty()) {
        return 0;
    }

    String delimiter = ",|\n";
    if (numbers.startsWith("//")) {
        String[] parts = numbers.split("\n", 2);
        delimiter = parts[0].substring(2);
        numbers = parts[1];
    }

    String[] tokens = numbers.split(delimiter);
    List<Integer> negatives = new ArrayList<>();
    int sum = 0;

    for (String token : tokens) {
        if (!token.isEmpty()) {
            int num = Integer.parseInt(token);
            if (num < 0) {
                negatives.add(num);
            } else if (num <= 1000) {
                sum += num;
            }
        }
    }

    if (!negatives.isEmpty()) {
        throw new IllegalArgumentException("Negatives not allowed: " + negatives.toString());
    }

    return sum;
}
}
