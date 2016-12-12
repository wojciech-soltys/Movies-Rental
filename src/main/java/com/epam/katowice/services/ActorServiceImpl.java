package com.epam.katowice.services;

import com.epam.katowice.dao.ActorRepository;
import com.epam.katowice.domain.Actor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Wojciech_Soltys on 29.08.2016.
 */
@Service
public class ActorServiceImpl implements ActorService {

    ActorRepository actorRepository;

    public ActorServiceImpl(@Autowired ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Override
    public List<Actor> findAll() {
        return actorRepository.findAll();
    }
}
