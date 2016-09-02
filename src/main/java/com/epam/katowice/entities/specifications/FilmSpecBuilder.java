package com.epam.katowice.entities.specifications;

import com.epam.katowice.controllers.parameters.Filters;
import com.epam.katowice.entities.Actor;
import com.epam.katowice.entities.Category;
import com.epam.katowice.entities.Film;
import com.epam.katowice.entities.Language;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wojciech_Soltys on 26.08.2016.
 */
public class FilmSpecBuilder {



    public Specification<Film> toSpecification(Filters filters) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(filters.getTitle())) {
                predicates.add(cb.like(root.get("title"), "%" + filters.getTitle() + "%"));
            }
            if (filters.getRelease_year_from() != null) {
                predicates.add(cb.ge(root.get("releaseYear"), filters.getRelease_year_from()));
            }
            if (filters.getRelease_year_to() != null) {
                predicates.add(cb.le(root.get("releaseYear"), filters.getRelease_year_to()));
            }
            if(StringUtils.isNotBlank(filters.getCategory()) && !"Any".equals(filters.getCategory())) {
                final Join<Film, Category> categories = root.join("categories");
                predicates.add(cb.like(categories.get("name"), "%" + filters.getCategory() + "%"));
            }
            if(StringUtils.isNoneBlank(filters.getLanguage())) {
                final Join<Film, Language> language = root.join("language");
                predicates.add(cb.like(language.get("name"), "%" + filters.getLanguage() + "%"));
            }
            if (filters.getMaxLength() != null && filters.getMaxLength() > 0) {
                predicates.add(cb.le(root.get("length"), filters.getMaxLength()));
            }
            if(StringUtils.isNotBlank(filters.getActor())) {
                final Join<Film, Actor> actors = root.join("actors");
                predicates.add(cb.or(cb.like(actors.get("first_name"), "%" + filters.getActor() + "%"), cb.like(actors.get("last_name"), "%" + filters.getActor() + "%")));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
