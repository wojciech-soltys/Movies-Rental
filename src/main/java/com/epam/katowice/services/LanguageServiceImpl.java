package com.epam.katowice.services;

import com.epam.katowice.dao.LanguageRepository;
import com.epam.katowice.domain.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Wojciech_Soltys on 26.08.2016.
 */
@Service
public class LanguageServiceImpl implements LanguageService{

    private LanguageRepository languageRepository;

    @Autowired
    public LanguageServiceImpl(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public List<Language> findAll() {
        return languageRepository.findAll();
    }
}
