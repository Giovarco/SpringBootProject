package net.mignemi.portfolio.featureTests.DesignApi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.mignemi.portfolio.dto.DesignDto;
import net.mignemi.portfolio.model.Design;
import net.mignemi.portfolio.model.Tag;
import net.mignemi.portfolio.repository.DesignRepository;
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
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DesignRetrievalTest {

    private Design savedDesign;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private DesignRepository designRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RepositoryUtils repositoryUtils;

    @Before
    public void setup() {
        byte[] byteArray = {1};

        List<Tag> tagList = new ArrayList<>();
        Tag tag = Tag.builder().title("tagTitle").build();
        tagList.add(tag);

        Design designToSave = Design.builder()
                .title("designTitle")
                .tags(tagList)
                .image(byteArray)
                .build();

        designRepository.save(designToSave);
        savedDesign = repositoryUtils.findUniqueDesign();
    }

    @Test
    public void test() throws Exception {
        // Call end-point
        String responseBody = mvc.perform(get("/design"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Process response
        List<DesignDto> designs = objectMapper.readValue(responseBody, new TypeReference<List<DesignDto>>() {
        });

        // Assert entity existance
        assertEquals(1, designs.size());

        // Assert entity content
        DesignDto retrievedDesign = designs.get(0);
        assertEquals(savedDesign.getTitle(), retrievedDesign.getTitle());
        assertTrue(Arrays.equals(savedDesign.getImage(), retrievedDesign.getImage()));
        assertEquals(designs.get(0).getTags().get(0), repositoryUtils.findUniqueTag().getId());
    }
}
