package com.epam.katowice.dao;

import com.epam.katowice.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Wojciech_Soltys on 29.08.2016.
 */
public interface ActorRepository extends JpaRepository<Actor, Long>, JpaSpecificationExecutor {
}
