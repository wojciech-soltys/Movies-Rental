package com.epam.katowice.services;

import com.epam.katowice.domain.User;

/**
 * Created by Wojciech_Soltys on 09.12.2016.
 */
public interface UserService {
    User findByName(String name);
    void saveUser(User user);
}
