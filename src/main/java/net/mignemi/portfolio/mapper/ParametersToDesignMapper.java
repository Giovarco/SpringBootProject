package net.mignemi.portfolio.mapper;

import net.mignemi.portfolio.model.Design;
import net.mignemi.portfolio.model.Tag;
import net.mignemi.portfolio.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ParametersToDesignMapper {

    @Autowired
    TagRepository tagRepository;

    public Design map(String title, List<Long> tags, MultipartFile file) {
        ;
        try {
            List<Tag> existingTags = tagRepository.findAllById(tags);
            Design designToSave = Design.builder()
                    .title(title)
                    .image(file.getBytes())
                    .tags(existingTags)
                    .build();
            existingTags.stream().forEach(tag -> {
                if (tag.getDesigns() == null) {
                    tag.setDesigns(new ArrayList<>());
                }
                tag.getDesigns().add(designToSave);
            });
            return designToSave;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }
}
