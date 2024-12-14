package com.project.jobmatch.helpers;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ParsingHelper {

    public ParsingHelper() {
    }

    public static Set<String> fromStringToSetStrings(String str) {
        if (str == null || str.trim().isEmpty()) {
            return Set.of();
        } else {
            return Arrays.stream(str.trim().split("\\s+"))
                    .map(String::toLowerCase)
                    .map(s -> s.replaceAll("[^a-z]", ""))
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toSet());
        }
    }
}
