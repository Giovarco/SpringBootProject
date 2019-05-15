package net.mignemi.portfolio.controller;

import net.mignemi.portfolio.model.Tag;
import net.mignemi.portfolio.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public List<Tag> getDesigns() {
        return tagService.getTags();
    }

    @PutMapping("/{id}")
    public void putDesign(@PathVariable("id") Long id, @RequestBody Tag tag) {
        tagService.updateTag(id, tag);
    }
}
