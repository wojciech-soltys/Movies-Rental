package com.epam.katowice.services;

import com.epam.katowice.domain.Actor;

import java.util.List;

/**
 * Created by Wojciech_Soltys on 29.08.2016.
 */
public interface ActorService {
    List<Actor> findAll();
}
