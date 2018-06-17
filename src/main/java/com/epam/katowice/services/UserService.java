package com.epam.katowice.services;

import com.epam.katowice.domain.User;
import com.epam.katowice.dto.UserDto;

/**
 * Created by Wojciech_Soltys on 09.12.2016.
 */
public interface UserService {
    User findByName(String name);
    User registerNewUserAccount(UserDto user) throws UserNameExistsException;
}
