package com.epam.katowice.entities;

/**
 * Created by Wojciech_Soltys on 05.09.2016.
 */
public enum Features {
    
    T("Trailers"),
    C("Commentaries"),
    D("Deleted Scenes"),
    B("Behind the Scenes");
    
    private String name;

    Features(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Features fromCode(String code) {
        if (code == null || code.equals("")) {
            return null;
        }

        for (Features feature : Features.values()) {
            if (feature.getName().equalsIgnoreCase(code)) {
                return feature;
            }
        }
        throw new IllegalArgumentException("A Rating for provided code: " + code + " cannot be found");
    }
}
