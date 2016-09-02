package com.epam.katowice.controllers;

import com.epam.katowice.common.MovieRentalTest;
import com.epam.katowice.controllers.parameters.Filters;
import com.epam.katowice.entities.Category;
import com.epam.katowice.entities.Film;
import com.epam.katowice.entities.Language;
import com.epam.katowice.entities.Rating;
import com.epam.katowice.services.CategoryService;
import com.epam.katowice.services.FilmService;
import com.epam.katowice.services.LanguageService;
import org.assertj.core.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Wojciech_Soltys on 10.08.2016.
 */
public class MovieRentalControllerTest extends MovieRentalTest {

    @Autowired
    WebApplicationContext webCtx;

    private MockMvc mockMvc;

    @Mock
    private FilmService filmService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private LanguageService languageService;

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
        Film film1 = new Film(1L, "title1", "description1", 2016, new Integer(100), Rating.NC17);
        Film film2 = new Film(2L, "title2", "description2", 2016, new Integer(200), Rating.NC17);

        Mockito.when(filmService.getByPredicate(Mockito.any(Filters.class), Mockito.any(Pageable.class))).thenReturn(new PageImpl<Film>(Arrays.asList(film1,film2)));
        Mockito.when(categoryService.findAll()).thenReturn(Arrays.asList(new Category()));
        Mockito.when(languageService.findAll()).thenReturn(Arrays.asList(new Language()));

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(view().name("films"))
            .andExpect(model().attribute("page", hasProperty("content", hasSize(2))))
            .andExpect(model().attribute("page", hasProperty("content", hasItem(
                allOf(
                    hasProperty("film_id", is(1L)),
                    hasProperty("description", is("description1")),
                    hasProperty("title", is("title1")),
                    hasProperty("releaseYear", is(2016)),
                    hasProperty("length", is(100))
                )
            ))))
           .andExpect(model().attribute("page", hasProperty("content", hasItem(
                allOf(
                    hasProperty("film_id", is(2L)),
                    hasProperty("description", is("description2")),
                    hasProperty("title", is("title2")),
                    hasProperty("releaseYear", is(2016)),
                    hasProperty("length", is(200))
                )
            ))));
    }
}