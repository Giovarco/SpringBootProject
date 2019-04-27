package net.mignemi.portfolio.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/design")
public class DesignController {

    @PostMapping
    public String greeting() {
        return "test";
    }

}
