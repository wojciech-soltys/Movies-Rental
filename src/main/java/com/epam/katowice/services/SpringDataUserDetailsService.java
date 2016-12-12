package com.epam.katowice.services;

import com.epam.katowice.domain.DatabaseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Wojciech_Soltys on 09.12.2016.
 */
@Service
public class SpringDataUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        DatabaseUser databaseUser = userService.findByName(name);

        if(databaseUser == null) {
            throw new UsernameNotFoundException(String.format("User with name: %s cannot be found", name));
        }
        String[] roles = new String[databaseUser.getRoles().size()];
        databaseUser.getRoles().toArray(roles);

        UserDetails userDetails =  new User(name, databaseUser.getPassword(), AuthorityUtils.createAuthorityList(roles));
        return userDetails;
    }
}
