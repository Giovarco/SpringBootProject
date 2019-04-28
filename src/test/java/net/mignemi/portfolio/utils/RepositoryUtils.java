package net.mignemi.portfolio.utils;

import net.mignemi.portfolio.model.Design;
import net.mignemi.portfolio.repository.DesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.junit.Assert.assertEquals;

@Component
public class RepositoryUtils {

    @Autowired
    DesignRepository designRepository;

    public Design findUniqueDesign() {
        List<Design> designs = designRepository.findAll();
        assertEquals(1, designs.size());
        return designs.get(0);
    }
}
