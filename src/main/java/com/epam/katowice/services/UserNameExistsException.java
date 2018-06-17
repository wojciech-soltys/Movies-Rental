package com.epam.katowice.services;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserNameExistsException extends Throwable {
    public UserNameExistsException(@NotNull @NotEmpty String userName) {
    }
}
