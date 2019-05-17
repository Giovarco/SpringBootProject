package net.mignemi.portfolio.mapper;

import net.mignemi.portfolio.model.Design;
import net.mignemi.portfolio.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Component
public class ParametersToDesignMapper {

    @Autowired
    TagRepository tagRepository;

    public Design map(String title, List<Long> tags, MultipartFile file) { ;
        try {
            return Design.builder()
                    .title(title)
                    .image(file.getBytes())
                    .tags(tagRepository.findAllById(tags))
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }
}
