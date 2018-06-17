package com.epam.katowice.configuration.security;

public enum Role {
    USER,ADMIN;

    public String getDatabaseRole() {
        return "ROLE_" + this.toString();
    }
}