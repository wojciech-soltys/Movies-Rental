package com.epam.katowice.services;

import com.epam.katowice.configuration.security.Role;
import com.epam.katowice.dao.UserRepository;
import com.epam.katowice.domain.User;
import com.epam.katowice.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Wojciech_Soltys on 09.12.2016.
 */
@Transactional
@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private StandardPasswordEncoder standardPasswordEncoder;

    @Override
    @Transactional(readOnly = true)
    public User findByName(String name) {
        return userRepository.findByUserName(name);
    }




    @Override
    public User registerNewUserAccount(UserDto userDto) throws UserNameExistsException {
        String userName = userDto.getUserName();
        if (userNameExist(userName)) {
            throw new UserNameExistsException(
                    "There is an account with that user name: "
                            +  userName);
        }
        User user = new User();
        user.setUserName(userName);
        user.setPassword(standardPasswordEncoder.encode(userDto.getPassword()));
        user.setRoles(new HashSet<>(Arrays.asList(Role.ADMIN.getDatabaseRole())));
        user.setEnabled(true);
        return userRepository.save(user);
    }

    private boolean userNameExist(String userName) {
        User user = userRepository.findByUserName(userName);
        if (user != null) {
            return true;
        }
        return false;
    }
}