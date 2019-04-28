package net.mignemi.portfolio.controller;

import net.mignemi.portfolio.model.Tag;
import net.mignemi.portfolio.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tag")
public class TagController {

    @Autowired
    TagService tagService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveTag(@RequestBody Tag tag) {
        tagService.saveTag(tag);
    }

}
