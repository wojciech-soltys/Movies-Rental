package com.epam.katowice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Wojciech_Soltys on 08.08.2016.
 */

@Controller
public class MovieRentalController {

    @RequestMapping("/")
    public String greeting() {
        return "index";
    }

}
