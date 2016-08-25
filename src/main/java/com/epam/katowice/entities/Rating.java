package com.epam.katowice.entities;

/**
 * Created by Wojciech_Soltys on 11.08.2016.
 */
public enum Rating {
    UNRATED("UNRATED"),
    G("G"),
    PG("PG"),
    PG13("PG-13"),
    R("R"),
    NC17("NC-17");

    private String rating;

    Rating(String rating) {
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }

    public static Rating fromCode(String code) {
        if (code == null) {
            return null;
        }

        for (Rating rating : Rating.values()) {
            if (rating.getRating().equalsIgnoreCase(code)) {
                return rating;
            }
        }
        throw new IllegalArgumentException("A Rating for provided code: " + code + " cannot be found");
    }
}
