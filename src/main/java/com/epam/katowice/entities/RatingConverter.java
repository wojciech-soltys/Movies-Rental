package com.epam.katowice.entities;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by Wojciech_Soltys on 11.08.2016.
 */
@Converter(autoApply = true)
public class RatingConverter implements AttributeConverter<Rating, String> {

    @Override
    public String convertToDatabaseColumn(Rating rating) {
        if (rating == null) {
            return null;
        }
        return rating.getRating();
    }

    @Override
    public Rating convertToEntityAttribute(String s) {
        return Rating.fromCode(s);
    }
}
