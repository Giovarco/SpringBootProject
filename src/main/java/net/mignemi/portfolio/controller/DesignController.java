package net.mignemi.portfolio.controller;

import net.mignemi.portfolio.dto.DesignDto;
import net.mignemi.portfolio.service.DesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/design")
public class DesignController {

    @Autowired
    DesignService designService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveDesign(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "tags") List<Long> tags,
            @RequestParam(value = "file") MultipartFile file) {
        designService.saveDesign(title, tags, file);
    }

    @GetMapping
    public List<DesignDto> getDesigns() {
        return designService.getDesigns();
    }

    @PutMapping("/{id}")
    public void updateDesign(
            @PathVariable("id") Long id,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "file") MultipartFile file,
            @RequestParam(value = "tags") List<Long> tagIds) {
        designService.updateDesign(id, title, file, tagIds);
    }

}
