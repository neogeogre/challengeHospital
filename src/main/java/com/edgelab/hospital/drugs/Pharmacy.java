package com.edgelab.hospital.drugs;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public enum Pharmacy {
    As(Aspirin::new),
    An(Antibiotic::new),
    I(Insulin::new),
    P(Paracetamol::new);

    public final Supplier<Drug> factory;

    Pharmacy(java.util.function.Supplier<Drug> factory) {
        this.factory = requireNonNull(factory);
    }

    public static List<Drug> pick(String drugstr) {
        return Arrays.stream(drugstr.split(","))
                .map(drug -> Pharmacy.valueOf(drug).factory.get())
                .collect(Collectors.toList());
    }

}
