package com.epam.katowice.entities;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Created by Wojciech_Soltys on 11.08.2016.
 */
@Converter(autoApply = true)
public class FeaturesConverter implements AttributeConverter<Set<Features>, String> {

    @Override
    public String convertToDatabaseColumn(Set<Features> features) {
        if (features == null) {
            return null;
        }
        return String.join(",", features.stream().map(Features::getName).collect(Collectors.toList()));
    }

    @Override
    public Set<Features> convertToEntityAttribute(String s) {

        if (s == null || s.equals("")) {
            return new HashSet<>();
        }

        return Arrays.stream(s.split(",")).map(Features::fromCode).collect(Collectors.toSet());
    }
}

