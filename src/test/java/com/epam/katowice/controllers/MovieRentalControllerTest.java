package com.epam.katowice.controllers;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.epam.katowice.common.MovieRentalTest;
import com.epam.katowice.dto.FilmDto;
import com.epam.katowice.services.FilmService;
import org.assertj.core.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by Wojciech_Soltys on 10.08.2016.
 */
public class MovieRentalControllerTest extends MovieRentalTest {

    @Autowired
    WebApplicationContext webCtx;

    private MockMvc mockMvc;

    @Mock
    private FilmService filmService;

    @InjectMocks
    private MovieRentalController movieController;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
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

    @Test
    public void testGetFilms() throws Exception {
        //when
        FilmDto filmDto1 = new FilmDto(1L, "title1", "description1", 2016, 100);
        FilmDto filmDto2 = new FilmDto(2L, "title2", "description2", 2016, 200);

        Mockito.when(filmService.getAllFilms()).thenReturn(Arrays.asList(filmDto1, filmDto2));

        mockMvc.perform(get("/movies"))
            .andExpect(status().isOk())
            .andExpect(view().name("films"))
            .andExpect(model().attribute("films", hasSize(2)))
            .andExpect(model().attribute("films", hasItem(
                allOf(
                    hasProperty("film_id", is(1L)),
                    hasProperty("description", is("description1")),
                    hasProperty("title", is("title1")),
                    hasProperty("release_year", is(2016)),
                    hasProperty("length", is(100))
                )
            )))
            .andExpect(model().attribute("films", hasItem(
                allOf(
                    hasProperty("film_id", is(2L)),
                    hasProperty("description", is("description2")),
                    hasProperty("title", is("title2")),
                    hasProperty("release_year", is(2016)),
                    hasProperty("length", is(200))
                )
            )));
    }

}