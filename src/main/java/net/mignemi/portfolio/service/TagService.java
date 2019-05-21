package net.mignemi.portfolio.service;

import net.mignemi.portfolio.converter.TagToTagDtoConverter;
import net.mignemi.portfolio.dto.TagDto;
import net.mignemi.portfolio.model.Tag;
import net.mignemi.portfolio.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    TagToTagDtoConverter tagToTagDtoConverter;

    public void saveTag(Tag tag) {
        tagRepository.save(tag);
    }

    public List<TagDto> getTags() {
        return tagRepository.findAll()
                .stream()
                .map(tag -> tagToTagDtoConverter.convert(tag))
                .collect(Collectors.toList());
    }

    public void updateTag(Long id, Tag tag) {
        Tag tagToSave = tagRepository.getOne(id);
        tagToSave.setTitle(tag.getTitle());
        tagRepository.save(tagToSave);
    }
}
