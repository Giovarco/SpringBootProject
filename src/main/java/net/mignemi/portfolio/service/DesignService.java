package net.mignemi.portfolio.service;

import net.mignemi.portfolio.mapper.ParametersToDesignMapper;
import net.mignemi.portfolio.model.Design;
import net.mignemi.portfolio.repository.DesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DesignService {

    @Autowired DesignRepository designRepository;

    public void saveDesign(String title, MultipartFile file) {
        Design design = ParametersToDesignMapper.map(title, file);
        designRepository.save(design);
    }
}
