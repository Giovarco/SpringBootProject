package net.mignemi.portfolio.featureTests.DesignApi;

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
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DesignUpdateTest {

    private static final String FILE_NAME_BEFORE = "testImage2.jpg";
    private static final String TITLE_BEFORE = "title";

    private static final String FILE_NAME_AFTER = "testImage.jpg";
    private static final String TITLE_AFTER = "updatedTitle";

    private MockMultipartFile newImage;

    private Long newTagId;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private DesignRepository designRepository;

    @Autowired
    private RepositoryUtils repositoryUtils;

    @Autowired
    private TagRepository tagRepository;

    @Before
    public void setup() throws IOException {
        saveDesignToUpdate();
        instantiateNewImage();
        saveNewTag();
    }

    @Test
    public void test() throws Exception {

        // Call end-point
        mvc.perform(
                getMockMultipartHttpPutServletRequestBuilder()
                        .file(newImage)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("title", TITLE_AFTER)
                        .param("tags", String.valueOf(newTagId))
        )
                .andExpect(status().isOk());

        // Assert entity existance
        List<Design> designs = designRepository.findAll();
        assertEquals(1, designs.size());

        Design design = designs.get(0);
        List<Tag> tags = design.getTags();
        assertEquals(1, tags.size());

        // Assert entity content
        assertEquals(TITLE_AFTER, design.getTitle());
        assertTrue(Arrays.equals(newImage.getBytes(), design.getImage()));
        assertEquals(Long.valueOf(newTagId), tags.get(tags.size()-1).getId());
    }

    private void instantiateNewImage() throws IOException {
        InputStream inputStream = new ClassPathResource(FILE_NAME_BEFORE).getInputStream();
        newImage = new MockMultipartFile("file", inputStream);
    }

    private void saveDesignToUpdate() throws IOException {
        InputStream inputStream = new ClassPathResource(FILE_NAME_AFTER).getInputStream();
        byte[] byteArray = toByteArray(inputStream);


        List<Tag> tagList = new ArrayList<>();
        tagList.add(Tag.builder().build());

        Design design = Design.builder()
                .title(TITLE_BEFORE)
                .image(byteArray)
                .tags(tagList)
                .build();

        designRepository.save(design);
    }

    /**
     * MockMultipartHttpServletRequestBuilder returned by MockMvcRequestBuilders.fileUpload() has the method hardcoded
     * to "POST". This is a workaround to use also for "PUT".
     */
    private MockMultipartHttpServletRequestBuilder getMockMultipartHttpPutServletRequestBuilder() {
        String urlTemplate = String.format("/design/%s", repositoryUtils.findUniqueDesign().getId());
        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.fileUpload(urlTemplate);
        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });
        return builder;
    }

    private static byte[] toByteArray(InputStream in) throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int len;

        // read bytes from the input stream and store them in buffer
        while ((len = in.read(buffer)) != -1) {
            // write bytes from the buffer into output stream
            os.write(buffer, 0, len);
        }

        return os.toByteArray();
    }

    private void saveNewTag() {
        Tag newTag = tagRepository.save(Tag.builder().build());
        newTagId = newTag.getId();
    }
}
