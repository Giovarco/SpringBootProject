package net.mignemi.portfolio.converter;

import net.mignemi.portfolio.dto.DesignDto;
import net.mignemi.portfolio.model.Design;
import net.mignemi.portfolio.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DesignToDesignDto
        implements Converter<Design, DesignDto> {

    @Override
    public DesignDto convert(Design design) {
        return DesignDto.builder()
                .id(design.getId())
                .image(design.getImage())
                .tags(design.getTags()
                        .stream()
                        .map(tag -> tag.getId())
                        .collect(Collectors.toList()))
                .title(design.getTitle())
                .build();
    }
}