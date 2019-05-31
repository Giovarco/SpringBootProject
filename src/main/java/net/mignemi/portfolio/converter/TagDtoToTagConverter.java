package net.mignemi.portfolio.converter;

import net.mignemi.portfolio.dto.TagDto;
import net.mignemi.portfolio.model.Design;
import net.mignemi.portfolio.model.Tag;
import net.mignemi.portfolio.repository.DesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagDtoToTagConverter
        implements Converter<TagDto, Tag> {

    @Autowired
    DesignRepository designRepository;

    @Override
    public Tag convert(TagDto tagDto) {
        return Tag.builder()
                .id(tagDto.getId())
                .title(tagDto.getTitle())
                .designs(designRepository.findAllById(tagDto.getDesignIds()))
                .build();
    }
}