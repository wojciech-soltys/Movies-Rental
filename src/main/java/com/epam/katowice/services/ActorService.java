package com.epam.katowice.services;

import com.epam.katowice.entities.Actor;

import java.util.List;

/**
 * Created by Wojciech_Soltys on 29.08.2016.
 */
public interface ActorService {

    public List<Actor> findAll();
}
