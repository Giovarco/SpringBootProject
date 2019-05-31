package net.mignemi.portfolio.featureTests.TagApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.mignemi.portfolio.dto.TagDto;
import net.mignemi.portfolio.model.Design;
import net.mignemi.portfolio.model.Tag;
import net.mignemi.portfolio.repository.DesignRepository;
import net.mignemi.portfolio.repository.TagRepository;
import net.mignemi.portfolio.utils.RepositoryUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TagCreationTest {

    private static final String TAG_TITLE = "tagTitle";
    private String request;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private DesignRepository designRepository;

    @Autowired
    private RepositoryUtils repositoryUtils;

    @Before
    public void setup() throws JsonProcessingException {
        Design design = Design.builder().build();
        designRepository.save(design);

        List<Long> designIds = new ArrayList<>();
        designIds.add(repositoryUtils.findUniqueDesign().getId());

        TagDto tag = TagDto.builder()
                .title(TAG_TITLE)
                .designIds(designIds)
                .build();
        request = objectMapper.writeValueAsString(tag);
    }

    @Test
    public void test() throws Exception {
        // Call end-point
        mvc.perform(post("/tag")
                .content(request)
                .contentType("application/json")
        ).andExpect(status().isCreated());

        // Assert entity existance
        List<Tag> tags = tagRepository.findAll();
        assertEquals(1, tags.size());

        // Assert entity content
        Tag tag = tags.get(0);
        assertEquals(TAG_TITLE, tag.getTitle());

        List<Design> tagDesigns = tag.getDesigns();
        assertEquals(1, tagDesigns.size());
        assertEquals(repositoryUtils.findUniqueDesign().getId(), tagDesigns.get(0).getId());
    }

}
