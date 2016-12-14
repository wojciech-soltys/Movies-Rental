package com.epam.katowice.services;

import com.epam.katowice.dao.UserRepository;
import com.epam.katowice.domain.DatabaseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Wojciech_Soltys on 09.12.2016.
 */
@Transactional(propagation = Propagation.SUPPORTS,
        readOnly = true)
@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public DatabaseUser findByName(String name) {
        return userRepository.findByUserName(name);
    }
}
