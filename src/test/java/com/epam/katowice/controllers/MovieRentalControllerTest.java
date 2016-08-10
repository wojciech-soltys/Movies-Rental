package com.epam.katowice.controllers;

import static org.testng.Assert.*;

import com.epam.katowice.MovieRentalApplicationTests;
import com.epam.katowice.services.FilmService;
import org.assertj.core.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ExtendedModelMap;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Wojciech_Soltys on 10.08.2016.
 */
public class MovieRentalControllerTest extends MovieRentalApplicationTests {

    @Mock
    private FilmService filmService;

    @InjectMocks
    private MovieRentalController movieController;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetFilmsCount() throws Exception {
        // when
        Mockito.when(filmService.getFilmsCount()).thenReturn(1000l);
        ExtendedModelMap model = new ExtendedModelMap();

        // then
        String index = movieController.getFilmsCount(model);

        //expected
        Assertions.assertThat(index).isEqualTo("index");
        Assertions.assertThat(model.asMap()).isNotEmpty();
        Assertions.assertThat(model.asMap()).containsKey("movieCount");
        Assertions.assertThat(model.asMap().get("movieCount")).isInstanceOf(Long.class);
        Assertions.assertThat((Long)model.asMap().get("movieCount")).isEqualTo(1000l);
    }

}