package com.edgelab.hospital.api;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Drug contains the known drugs,
 * just add an enum in the list if needed.
 */
public enum Drug {

    ASPIRIN("As"),
    ANTIBIOTIC("An"),
    INSULIN("I"),
    PARACETAMOL("P");

    public final String drugCode;

    Drug(String drugsCodeStr) {
        this.drugCode = drugsCodeStr;
    }

    public static List<Drug> parseDrugs(String input) {
        return Arrays.stream(input.split(","))
                .map(Drug::parseDrug)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private static Optional<Drug> parseDrug(String drugString) {
        return Stream.of(Drug.values())
                .filter(drug -> drug.drugCode.equals(drugString))
                .findFirst();
    }

}
