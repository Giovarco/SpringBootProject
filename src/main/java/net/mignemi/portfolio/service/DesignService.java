package net.mignemi.portfolio.service;

import net.mignemi.portfolio.converter.DesignToDesignDto;
import net.mignemi.portfolio.dto.DesignDto;
import net.mignemi.portfolio.mapper.ParametersToDesignMapper;
import net.mignemi.portfolio.model.Design;
import net.mignemi.portfolio.repository.DesignRepository;
import net.mignemi.portfolio.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DesignService {

    @Autowired
    DesignRepository designRepository;

    @Autowired
    ParametersToDesignMapper parametersToDesignMapper;

    @Autowired
    DesignToDesignDto designToDesignDto;

    @Autowired
    TagRepository tagRepository;

    public void saveDesign(String title, List<Long> tags, MultipartFile file) {
        Design design = parametersToDesignMapper.map(title, tags, file);
        designRepository.save(design);
    }

    public List<DesignDto> getDesigns() {
        return designRepository.findAll()
                .stream()
                .map(design -> designToDesignDto.convert(design))
                .collect(Collectors.toList());
    }

    public void updateDesign(Long id, String title, MultipartFile file, List<Long> tagIds) {
        try {
            getAndUpdateDesign(id, title, file, tagIds);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    public void getAndUpdateDesign(Long id, String title, MultipartFile file, List<Long> tagIds) throws IOException {
        Design designInDb = designRepository.getOne(id);
        designInDb.setTitle(title);
        designInDb.setImage(file.getBytes());
        designInDb.setTags(tagRepository.findAllById(tagIds));
        designRepository.save(designInDb);
    }
}
