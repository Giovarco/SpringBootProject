package net.mignemi.portfolio.featureTests;

import net.mignemi.portfolio.model.Design;
import net.mignemi.portfolio.repository.DesignRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DesignCreationTest {

    private static final String TITLE = "title";

    private MockMultipartFile image;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private DesignRepository designRepository;

    @Before
    public void setup() throws IOException {
        InputStream inputStream = new ClassPathResource("testImage.jpg").getInputStream();
        image = new MockMultipartFile("file", inputStream);
    }

    @Test
    public void test() throws Exception {
        // Call end-point
        mvc.perform(
                multipart("/design")
                        .file(image)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param(TITLE, TITLE)
        )
                .andExpect(status().isCreated());

        // Assert entity existance
        List<Design> designs = designRepository.findAll();
        assertEquals(1, designs.size());

        // Assert entity content
        Design design = designs.get(0);
        assertEquals(TITLE, design.getTitle());
        assertNotNull(design.getImage());
    }
}
