package com.edgelab.hospital;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Drug contains the known drugs,
 * just add an enum in the list if needed.
 */
public enum Drug {

    As("Aspirin"),
    An("Antibiotic"),
    I("Insulin"),
    P("Paracetamol");

    Drug(String name) {
    }

    public static List<Drug> parse(String input) {
        var parts = Arrays.asList(input.split(","));
        return parts.stream()
                .map(str -> {
                    try {
                        return Drug.valueOf(str);
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
