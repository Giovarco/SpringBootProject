package net.mignemi.portfolio.converter;

import net.mignemi.portfolio.dto.TagDto;
import net.mignemi.portfolio.model.Tag;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TagToTagDtoConverter
        implements Converter<Tag, TagDto> {

    @Override
    public TagDto convert(Tag tag) {
        return TagDto.builder()
                .designIds(tag.getDesigns()
                        .stream()
                        .map(design -> design.getId())
                        .collect(Collectors.toList()))
                .id(tag.getId())
                .title(tag.getTitle())
                .build();
    }
}