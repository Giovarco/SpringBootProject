package net.mignemi.portfolio.service;

import net.mignemi.portfolio.mapper.ParametersToDesignMapper;
import net.mignemi.portfolio.model.Design;
import net.mignemi.portfolio.repository.DesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class DesignService {

    @Autowired DesignRepository designRepository;

    public void saveDesign(String title, MultipartFile file) {
        Design design = ParametersToDesignMapper.map(title, file);
        designRepository.save(design);
    }

    public List<Design> getDesigns() {
        return designRepository.findAll();
    }

    public void updateDesign(Long id, String title, MultipartFile file) {
        try {
            getAndUpdateDesign(id, title, file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    public void getAndUpdateDesign(Long id, String title, MultipartFile file) throws IOException {
        Design designInDb = designRepository.getOne(id);
        designInDb.setTitle(title);
        designInDb.setImage(file.getBytes());
        designRepository.save(designInDb);
    }
}
