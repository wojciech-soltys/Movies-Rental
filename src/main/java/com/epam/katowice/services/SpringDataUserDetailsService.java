package com.epam.katowice.services;

import com.epam.katowice.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
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

        User user = userService.findByName(name);

        if(user == null) {
            throw new UsernameNotFoundException(String.format("User with name: %s cannot be found", name));
        }
        String[] roles = new String[user.getRoles().size()];
        user.getRoles().toArray(roles);

        UserDetails userDetails =  new org.springframework.security.core.userdetails.User(name, user.getPassword(), AuthorityUtils.createAuthorityList(roles));
        return userDetails;
    }
}
