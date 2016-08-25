package com.epam.katowice.controllers;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.epam.katowice.common.MovieRentalTest;
import com.epam.katowice.dto.FilmDto;
import com.epam.katowice.entities.Film;
import com.epam.katowice.services.FilmService;
import org.assertj.core.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Locale;

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
        mockMvc = MockMvcBuilders.standaloneSetup(movieController)
                .setCustomArgumentResolvers(new HateoasPageableHandlerMethodArgumentResolver())
                .setViewResolvers(new ViewResolver() {
                    @Override
                    public View resolveViewName(String viewName, Locale locale) throws Exception {
                        return new MappingJackson2JsonView();
                    }
                })
                .build();
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
        Film film1 = new Film(1L, "title1", "description1", 2016, 100);
        Film film2 = new Film(2L, "title2", "description2", 2016, 200);

        Pageable pageRequest = new PageRequest(0,1, Sort.Direction.DESC, "title");

        Mockito.when(filmService.getPageOfFilms(Mockito.any(Pageable.class))).thenReturn(new PageImpl<Film>(Arrays.asList(film1,film2)));
        Mockito.when(filmService.getPageOfFilms(Mockito.any(Pageable.class))).thenReturn(new PageImpl<Film>(Arrays.asList(film1,film2)));

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(view().name("films"))
            .andExpect(model().attribute("page", hasProperty("content", hasSize(2))))
            .andExpect(model().attribute("page", hasProperty("content", hasItem(
                allOf(
                    hasProperty("film_id", is(1L)),
                    hasProperty("description", is("description1")),
                    hasProperty("title", is("title1")),
                    hasProperty("release_year", is(2016)),
                    hasProperty("length", is(100))
                )
            ))))
           .andExpect(model().attribute("page", hasProperty("content", hasItem(
                allOf(
                    hasProperty("film_id", is(2L)),
                    hasProperty("description", is("description2")),
                    hasProperty("title", is("title2")),
                    hasProperty("release_year", is(2016)),
                    hasProperty("length", is(200))
                )
            ))));
    }
}