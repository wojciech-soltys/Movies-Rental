package com.epam.katowice.common;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * Created by Wojciech_Soltys on 10.08.2016.
 */

@SpringBootTest
@ActiveProfiles("test")
@EnableSpringDataWebSupport
public class MovieRentalTest extends AbstractTestNGSpringContextTests {

}
