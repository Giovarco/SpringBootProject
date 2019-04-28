package net.mignemi.portfolio.featureTests.TagApi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.mignemi.portfolio.model.Tag;
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

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TagRetrievalTest {

    private static final String TITLE = "tagTitle";

    private Tag savedTag;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RepositoryUtils repositoryUtils;

    @Before
    public void setup() {
        Tag tagToSave = Tag.builder()
                .title(TITLE)
                .build();
        tagRepository.save(tagToSave);
        savedTag = repositoryUtils.findUniqueTag();
    }

    @Test
    public void test() throws Exception {
        // Call end-point
        String responseBody = mvc.perform(get("/tag"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Process response
        List<Tag> responseTags = objectMapper.readValue(responseBody, new TypeReference<List<Tag>>() {
        });

        // Assert response tag existance
        assertEquals(1, responseTags.size());

        // Assert response tag content
        Tag responseTag = responseTags.get(0);
        assertEquals(savedTag.getTitle(), responseTag.getTitle());
    }
}
