package com.epam.katowice.controllers;

import com.epam.katowice.domain.User;
import com.epam.katowice.dto.UserDto;
import com.epam.katowice.services.UserNameExistsException;
import com.epam.katowice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller()
public class UserController {

    public static final String CREATE_USER_ENDPOINT = "/createUser";

    static final String REGISTRATION_VIEW = "registration";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = CREATE_USER_ENDPOINT, method = RequestMethod.GET)
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return REGISTRATION_VIEW;
    }

    @RequestMapping(value = CREATE_USER_ENDPOINT, method = RequestMethod.POST)
    public ModelAndView registerUserAccount
            (@ModelAttribute("user") @Valid UserDto userDto,
             BindingResult result, WebRequest request, Errors errors) {
        User registered = new User();
        if (!result.hasErrors()) {
            registered = createUserAccount(userDto, result);
        }
        if (registered == null) {
            result.rejectValue("userName", "message.regError");
        }
        if (result.hasErrors()) {
            return new ModelAndView(REGISTRATION_VIEW, "user", userDto);
        }
        else {
            return new ModelAndView(MovieRentalController.INDEX_VIEW, "user", userDto);
        }
    }

    private User createUserAccount(UserDto userDto, BindingResult result) {
        User registered = null;
        try {
            registered = userService.registerNewUserAccount(userDto);
        } catch (UserNameExistsException e) {
            return null;
        }
        return registered;
    }
}
