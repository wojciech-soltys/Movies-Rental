package com.epam.katowice.services;

import com.epam.katowice.common.MovieRentalTest;
import com.epam.katowice.dao.FilmRepository;
import org.assertj.core.api.Assertions;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Wojciech_Soltys on 09.08.2016.
 */
public class FilmServiceImplTest extends MovieRentalTest {

    @Mock
    private FilmRepository repository;

    private FilmService filmService;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        filmService = new FilmServiceImpl(repository);
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetFilmsCount() throws Exception {
        // when
        Mockito.when(repository.count()).thenReturn(1000l);

        // then
        long movieCount = filmService.getFilmsCount();

        //expected
        Assertions.assertThat(movieCount).isEqualTo(1000l);
    }

}