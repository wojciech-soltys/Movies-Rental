package com.epam.katowice.services;

import com.epam.katowice.entities.Actor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Wojciech_Soltys on 29.08.2016.
 */
@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    ActorService actorService;

    @Override
    public List<Actor> findAll() {
        return actorService.findAll();
    }
}
