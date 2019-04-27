package net.mignemi.portfolio.controller;

import net.mignemi.portfolio.dto.DesignDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/design")
public class DesignController {

    @PostMapping
    public void saveDesign(@RequestBody DesignDto designDto) {
        System.out.println(designDto);
    }

}
