package com.epam.katowice.dao;

import com.epam.katowice.domain.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Wojciech_Soltys on 26.08.2016.
 */
public interface LanguageRepository extends JpaRepository<Language, Long>, JpaSpecificationExecutor {

}
