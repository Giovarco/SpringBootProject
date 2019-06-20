package net.mignemi.portfolio.service;

import net.mignemi.portfolio.converter.TagDtoToTagConverter;
import net.mignemi.portfolio.converter.TagToTagDtoConverter;
import net.mignemi.portfolio.dto.TagDto;
import net.mignemi.portfolio.model.Design;
import net.mignemi.portfolio.model.Tag;
import net.mignemi.portfolio.repository.DesignRepository;
import net.mignemi.portfolio.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
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
        List<Design> oldDesigns = tagToSave.getDesigns();

        tagToSave.setTitle(tagDto.getTitle());

        // Assign the new designs to the tag
        List<Design> newDesigns = designRepository.findAllById(tagDto.getDesignIds());
        tagToSave.setDesigns(newDesigns);
        tagRepository.save(tagToSave);

        // Remove the tag from the old designs
        List<Long> oldDesignIds = oldDesigns.stream().map(design -> design.getId()).collect(Collectors.toList());
        List<Design> oldDesignEntities = designRepository.findAllById(oldDesignIds);
        oldDesignEntities.stream().forEach(oldDesignEntity -> {
            Iterator<Tag> i = oldDesignEntity.getTags().iterator();
            while (i.hasNext()) {
                Tag tag = i.next();
                if(tag.getId()) // TODO
                i.remove();
            }
        });
        designRepository.saveAll(oldDesignEntities);
    }
}
