package net.mignemi.portfolio.featureTests.TagApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.mignemi.portfolio.model.Tag;
import net.mignemi.portfolio.repository.TagRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

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

    @Before
    public void setup() throws JsonProcessingException {
        Tag tag = Tag.builder()
                .title(TAG_TITLE)
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
    }

}
