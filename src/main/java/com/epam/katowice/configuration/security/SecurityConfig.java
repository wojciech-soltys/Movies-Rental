package com.epam.katowice.configuration.security;

/**
 * Created by Wojciech_Soltys on 15.11.2016.
 */

import com.epam.katowice.controllers.MovieRentalController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@Profile({"prod","test"})
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    static final int COOKIE_VALIDITY_TIME_IN_SECONDS = 28800;
    static final String REMEMBER_ME_COOKIE = "remember-me";
    static final String JSESSIONID = "JSESSIONID";

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private StandardPasswordEncoder standardPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

            http
                .authorizeRequests()
                    .antMatchers( "/", "/static/**").permitAll()
                    .antMatchers(MovieRentalController.NEW_MOVIE_ENDPOINT).hasRole(Role.ADMIN.name())
                    .antMatchers(MovieRentalController.MOVIES_LIST_ENDPOINT).hasAnyRole(Role.USER.name(),Role.ADMIN.name())
                    .antMatchers(MovieRentalController.INDEX_ENDPOINT).permitAll()
                    .and()
                .formLogin()
                    .loginPage(MovieRentalController.LOGIN_ENDPOINT)
                    .failureUrl(MovieRentalController.LOGIN_ERROR_ENDPOINT)
                    .successForwardUrl(MovieRentalController.MOVIES_LIST_ENDPOINT)
                    .permitAll()
                    .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher(MovieRentalController.LOGOUT_ENDPOINT))
                    .logoutSuccessUrl(MovieRentalController.LOGOUT_DONE_ENDPOINT)
                    .invalidateHttpSession(true)
                    .deleteCookies(JSESSIONID)
                    .deleteCookies(REMEMBER_ME_COOKIE)
                    .permitAll()
                    .and()
                .httpBasic()
                .and()
                .csrf()
                .and()
                .rememberMe()
                    .rememberMeCookieName(REMEMBER_ME_COOKIE)
                    .tokenValiditySeconds(COOKIE_VALIDITY_TIME_IN_SECONDS)
                .and()
                    .exceptionHandling().accessDeniedPage(MovieRentalController.ACCESS_DENIED_ENDPOINT);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(standardPasswordEncoder);
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
