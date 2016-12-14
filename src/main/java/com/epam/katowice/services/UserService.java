package com.epam.katowice.services;

import com.epam.katowice.domain.DatabaseUser;

/**
 * Created by Wojciech_Soltys on 09.12.2016.
 */
public interface UserService {
    DatabaseUser findByName(String name);
}
