package net.mignemi.portfolio.converter;

import net.mignemi.portfolio.dto.TagDto;
import net.mignemi.portfolio.model.Design;
import net.mignemi.portfolio.model.Tag;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagToTagDtoConverter
        implements Converter<Tag, TagDto> {

    @Override
    public TagDto convert(Tag tag) {
        TagDto.TagDtoBuilder tagDtoBuilder = TagDto.builder()
                .id(tag.getId())
                .title(tag.getTitle());

        List<Design> tagDesigns = tag.getDesigns();
        if (tagDesigns != null) {
            tagDtoBuilder.designIds(tagDesigns.stream()
                    .map(design -> design.getId())
                    .collect(Collectors.toList()));
        }

        return tagDtoBuilder.build();
    }
}