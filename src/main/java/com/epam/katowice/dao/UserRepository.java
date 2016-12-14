package com.epam.katowice.dao;

import com.epam.katowice.domain.DatabaseUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Wojciech_Soltys on 09.12.2016.
 */
public interface UserRepository extends JpaRepository<DatabaseUser,Long> {
    DatabaseUser findByUserName(String name);
}
