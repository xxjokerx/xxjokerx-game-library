package org.motoc.gamelibrary.repository.fragment.implementation;

import org.motoc.gamelibrary.model.Game;
import org.motoc.gamelibrary.model.Theme;
import org.motoc.gamelibrary.repository.fragment.ThemeFragmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 * It's the theme custom repository implementation, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
@Repository
public class ThemeFragmentRepositoryImpl implements ThemeFragmentRepository {

    private static final Logger logger = LoggerFactory.getLogger(ThemeFragmentRepositoryImpl.class);

    private final EntityManager entityManager;

    @Autowired
    public ThemeFragmentRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void remove(Long id) {
        Theme theme = entityManager.find(Theme.class, id);
        if (theme != null) {
            for (Game game : theme.getGames()) {
                game.removeTheme(theme);
            }
            entityManager.remove(theme);
            logger.info("Successfully deleted theme of id={}", id);
        } else
            logger.info("Tried to delete, but theme of id={} doesn't exist", id);
    }
}