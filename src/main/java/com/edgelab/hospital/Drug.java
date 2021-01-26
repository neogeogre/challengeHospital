package com.edgelab.hospital;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public enum Drug {

    As("Aspirin"),
    An("Antibiotic"),
    I("Insulin"),
    P("Paracetamol");

    Drug(String name) {
    }

    public static List<Drug> parse(String input) {
        List<String> parts = Arrays.asList(input.split(","));
        return parts.stream()
                .map(str -> {try {return Drug.valueOf(str);} catch(RuntimeException e) {return null;}})
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
