package net.mignemi.portfolio.controller;

import net.mignemi.portfolio.model.Design;
import net.mignemi.portfolio.repository.DesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/design")
public class DesignController {

    @Autowired
    DesignRepository designRepository;

    @PostMapping
    public void saveDesign(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "file") MultipartFile file) {
        Design design = null;
        try {
            design = Design.builder()
                    .title(title)
                    .image(file.getBytes())
                    .build();
            designRepository.save(design);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
