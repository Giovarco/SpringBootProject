package net.mignemi.portfolio.controller;

import net.mignemi.portfolio.model.Design;
import net.mignemi.portfolio.service.DesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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
            @RequestParam(value = "file") MultipartFile file) {
        designService.saveDesign(title, file);
    }

    @GetMapping
    public List<Design> getDesigns() {
        return designService.getDesigns();
    }

    @PutMapping("/{id}")
    public void updateDesign(
            @PathVariable("id") Long id,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "file") MultipartFile file) {
        designService.updateDesign(id, title, file);
    }

}
