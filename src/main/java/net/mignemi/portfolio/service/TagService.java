package net.mignemi.portfolio.service;

import net.mignemi.portfolio.converter.TagDtoToTagConverter;
import net.mignemi.portfolio.converter.TagToTagDtoConverter;
import net.mignemi.portfolio.dto.TagDto;
import net.mignemi.portfolio.model.Tag;
import net.mignemi.portfolio.repository.DesignRepository;
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

    @Autowired
    TagDtoToTagConverter tagDtoToTagConverter;

    @Autowired
    DesignRepository designRepository;

    public void saveTag(TagDto tagDto) {
        Tag tag = tagDtoToTagConverter.convert(tagDto);
        designRepository.findAllById(tagDto.getDesignIds()).forEach(design -> {
            List<Tag> designTags = design.getTags();
            if(designTags != null) {
                designTags.add(tag);
            }
        });
        tagRepository.save(tag);
    }

    public List<TagDto> getTags() {
        return tagRepository.findAll()
                .stream()
                .map(tag -> tagToTagDtoConverter.convert(tag))
                .collect(Collectors.toList());
    }

    public void updateTag(Long id, TagDto tagDto) {
        Tag tagToSave = tagRepository.getOne(id);
        tagToSave.setTitle(tagDto.getTitle());
        tagRepository.save(tagToSave);
    }
}
