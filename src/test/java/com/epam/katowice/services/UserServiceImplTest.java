package com.epam.katowice.services;

import com.epam.katowice.common.MovieRentalTest;
import com.epam.katowice.dao.FilmRepository;
import com.epam.katowice.dao.UserRepository;
import com.epam.katowice.domain.User;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class UserServiceImplTest extends MovieRentalTest {

    @Mock
    private UserRepository repository;

    private UserService userService;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(repository);
    }

    @AfterMethod
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void testFindByName() {
        //given
        User user = new User();
        user.setId(1L);
        user.setUserName("admin");
        user.setPassword("acvzxcsdsdf#");
        user.setEnabled(true);

        when(repository.findByUserName(Mockito.any(String.class))).thenReturn(user);

       //when
        User userFromService = userService.findByName("admin");

        //then
        assertThat(userFromService.getId()).isEqualTo(user.getId());
        assertThat(userFromService.getUserName()).isEqualTo(user.getUserName());
        assertThat(userFromService.getPassword()).isEqualTo(user.getPassword());
        assertThat(userFromService.isEnabled()).isEqualTo(user.isEnabled());
    }

    @Test
    public void testRegisterNewUserAccount() {
    }
}