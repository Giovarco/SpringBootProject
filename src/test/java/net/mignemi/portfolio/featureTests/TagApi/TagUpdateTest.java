package net.mignemi.portfolio.featureTests.TagApi;

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

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TagUpdateTest {

    private static final String TITLE_BEFORE = "title";
    private static final String TITLE_AFTER = "updatedTitle";

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
        Tag tag = Tag.builder().title(TITLE_BEFORE).build();
        tagRepository.save(tag);
    }

    @Test
    public void test() throws Exception {
        // Create request body
        Tag updatedTag = Tag.builder().title(TITLE_AFTER).build();
        String request = objectMapper.writeValueAsString(updatedTag);

        // Call end-point
        String urlTemplate = String.format("/tag/%s", repositoryUtils.findUniqueTag().getId());
        mvc.perform(put(urlTemplate)
                .content(request)
                .contentType("application/json")
        ).andExpect(status().isOk());

        // Assert entity existance
        Tag tag = repositoryUtils.findUniqueTag();

        // Assert entity content
        assertEquals("updatedTitle", tag.getTitle());
    }
}
