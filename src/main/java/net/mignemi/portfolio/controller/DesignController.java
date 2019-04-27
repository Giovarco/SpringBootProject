package net.mignemi.portfolio.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/design")
public class DesignController {

    @PostMapping
    public void saveDesign(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "file") MultipartFile file) {
        System.out.println(title);
        System.out.println(file);
    }

}
