package com.epam.katowice.controllers;

import com.epam.katowice.controllers.parameters.Filters;
import com.epam.katowice.dto.FilmDto;
import com.epam.katowice.dto.wrappers.PageWrapper;
import com.epam.katowice.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Map;

@Controller
public class MovieRentalController {

    private static Logger logger = LoggerFactory.getLogger(MovieRentalController.class);

    public static final String INDEX_ENDPOINT = "/index";
    public static final String NEW_MOVIE_ENDPOINT = "/addMovie";
    public static final String DETAILS_ENDPOINT = "/details";
    public static final String MOVIES_LIST_ENDPOINT = "/movies";
    public static final String LOGIN_ENDPOINT = "/login";
    public static final String LOGIN_ERROR_ENDPOINT = "/login-error";
    public static final String LOGOUT_ENDPOINT = "/logout";
    public static final String LOGOUT_DONE_ENDPOINT = "/logout-done";
    public static final String ACCESS_DENIED_ENDPOINT = "/403";

    static final String REDIRECT_PREFIX = "redirect:";
    static final String INDEX_VIEW = "index";
    static final String NEW_MOVIE_VIEW = "addMovie";
    static final String DETAILS_VIEW = "details";
    static final String MOVIES_LIST_VIEW = "films";
    static final String LOGIN_VIEW = "login";
    static final String ACCESS_DENIED_VIEW = "403";

    static final String MOVIE_COUNT_PARAMETER = "movieCount";
    static final String PAGE_PARAMETER = "page";
    static final String PAGE_SIZE_PARAMETER = "size";
    static final String PAGE_SORT_PARAMETER = "sort";
    static final String ID_PARAMETER = "id";
    static final String FILM_PARAMETER = "film";
    static final String CATEGORIES_PARAMETER = "categories";
    static final String LANGUAGES_PARAMETER = "languages";
    static final String ACTORS_PARAMETER = "actors";

    private FilmService filmService;
    private final CategoryService categoryService;
    private final LanguageService languageService;
    private final ActorService actorService;

    @Autowired
    public MovieRentalController(FilmService filmService, CategoryService categoryService,
                                 LanguageService languageService, ActorService actorService) {
        this.filmService = filmService;
        this.categoryService = categoryService;
        this.languageService = languageService;
        this.actorService = actorService;
    }

    @RequestMapping(LOGIN_ENDPOINT)
    public String login() {
        return LOGIN_VIEW;
    }

    @RequestMapping(LOGIN_ERROR_ENDPOINT)
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return LOGIN_VIEW;
    }

    @RequestMapping(LOGOUT_DONE_ENDPOINT)
    public String logout(Model model) {
        model.addAttribute("logout", true);
        return LOGIN_VIEW;
    }

    @RequestMapping(ACCESS_DENIED_ENDPOINT)
    public String accessDenied(Model model, Principal user) {
        model.addAttribute("username", user == null ? "" : user.getName());
        return ACCESS_DENIED_VIEW;
    }

    @RequestMapping(INDEX_ENDPOINT)
    public String getFilmsCount(Model model) {
        model.addAttribute(MOVIE_COUNT_PARAMETER, filmService.getFilmsCount());
        return INDEX_VIEW;
    }

    @RequestMapping(MOVIES_LIST_ENDPOINT)
    public String getFilms(Model model, Pageable pageable, Filters filters, @RequestParam Map<String,String> allRequestParams) {
        PageWrapper<FilmDto> page = new PageWrapper<>(filmService.getByPredicate(filters, pageable),
                MOVIES_LIST_ENDPOINT + generateSearchLink(allRequestParams));

        model.addAttribute(PAGE_PARAMETER, page);
        addSortParameter(model, page);
        prepareDictionaries(model);
        return MOVIES_LIST_VIEW;
    }

    @RequestMapping(DETAILS_ENDPOINT)
    public String view(@RequestParam Long id, Model model) {
        FilmDto film = filmService.findById(id);
        model.addAttribute(FILM_PARAMETER, film);
        return DETAILS_VIEW;
    }

    @RequestMapping(NEW_MOVIE_ENDPOINT)
    public String view(Model model, FilmDto filmDto) {
        prepareDictionaries(model);
        return NEW_MOVIE_VIEW;
    }


    @RequestMapping(value = NEW_MOVIE_ENDPOINT, method = RequestMethod.POST)
    public String addMovie(Model model, @Validated FilmDto filmDto, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            prepareDictionaries(model);
            return NEW_MOVIE_VIEW;
        }

        FilmDto filmOutput = filmService.save(filmDto);
        prepareDictionaries(model);
        redirectAttributes.addAttribute(ID_PARAMETER, filmOutput.getId());
        return REDIRECT_PREFIX + DETAILS_VIEW;
    }

    private void prepareDictionaries(Model model) {
        model.addAttribute(CATEGORIES_PARAMETER, categoryService.findAll());
        model.addAttribute(LANGUAGES_PARAMETER, languageService.findAll());
        model.addAttribute(ACTORS_PARAMETER, actorService.findAll());
    }

    private String generateSearchLink(Map<String,String> allRequestParams) {
        String link = "?";
            for (Map.Entry<String, String> entry : allRequestParams.entrySet())
            {
                if(!entry.getKey().equals(PAGE_PARAMETER) && !entry.getKey().equals(PAGE_SIZE_PARAMETER)
                        && !entry.getKey().equals(PAGE_SORT_PARAMETER)) {
                    link += entry.getKey() + "=" + entry.getValue();
                    link += "&";
                }
            }

        return link;
    }

    private void addSortParameter(Model model, PageWrapper<FilmDto> page) {
        if(page.getPage().getSort() != null) {
            model.addAttribute(PAGE_SORT_PARAMETER, page.getPage().getSort().toString().replaceAll(": ", ","));
        } else {
            model.addAttribute(PAGE_SORT_PARAMETER, "");
        }
    }
}