package net.mignemi.portfolio.service;

import net.mignemi.portfolio.model.Tag;
import net.mignemi.portfolio.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;

    public void saveTag(Tag tag) {
        tagRepository.save(tag);
    }
}
