package net.mignemi.portfolio.utils;

import net.mignemi.portfolio.model.Design;
import net.mignemi.portfolio.model.Tag;
import net.mignemi.portfolio.repository.DesignRepository;
import net.mignemi.portfolio.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * This class contains utility methods to prevent database inconsistencies in advance (e.g. it could happen that one
 * forgets @Transactional annotation in a test class and the database is not emptied after each test)
 */
@Component
public class RepositoryUtils {

    @Autowired
    DesignRepository designRepository;

    @Autowired
    TagRepository tagRepository;

    public Design findUniqueDesign() {
        List<Design> designs = designRepository.findAll();
        assertEquals(1, designs.size());
        return designs.get(0);
    }

    public Tag findUniqueTag() {
        List<Tag> tags = tagRepository.findAll();
        assertEquals(1, tags.size());
        return tags.get(0);
    }
}
