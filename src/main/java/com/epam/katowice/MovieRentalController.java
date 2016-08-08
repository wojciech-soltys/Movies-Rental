package com.epam.katowice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Wojciech_Soltys on 08.08.2016.
 */

@RestController
public class MovieRentalController {

    @RequestMapping("/")
    public String greeting() {
        return "Welcome in Movies Rental!";
    }

}
