package net.mignemi.portfolio.featureTests.DesignApi;

import net.mignemi.portfolio.model.Design;
import net.mignemi.portfolio.repository.DesignRepository;
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

    @Autowired
    private MockMvc mvc;

    @Autowired
    private DesignRepository designRepository;

    @Before
    public void setup() throws IOException {
        saveDesignToUpdate();
        instantiateNewImage();
    }

    @Test
    public void test() throws Exception {

        // Call end-point
        mvc.perform(
                getMockMultipartHttpPutServletRequestBuilder()
                        .file(newImage)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("title", TITLE_AFTER)
        )
                .andExpect(status().isOk());

        // Assert entity existance
        List<Design> designs = designRepository.findAll();
        assertEquals(1, designs.size());

        // Assert entity content
        Design design = designs.get(0);
        assertEquals(TITLE_AFTER, design.getTitle());
        assertTrue(Arrays.equals(newImage.getBytes(), design.getImage()));
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

    private void instantiateNewImage() throws IOException {
        InputStream inputStream = new ClassPathResource(FILE_NAME_BEFORE).getInputStream();
        newImage = new MockMultipartFile("file", inputStream);
    }

    private void saveDesignToUpdate() throws IOException {
        InputStream inputStream = new ClassPathResource(FILE_NAME_AFTER).getInputStream();
        byte[] byteArray = toByteArray(inputStream);

        Design design = Design.builder()
                .title(TITLE_BEFORE)
                .image(byteArray)
                .build();

        designRepository.save(design);
    }

    /**
     * MockMultipartHttpServletRequestBuilder returned by MockMvcRequestBuilders.fileUpload() has the method hardcoded
     * to "POST". This is a workaround to use also for "PUT".
     */
    private MockMultipartHttpServletRequestBuilder getMockMultipartHttpPutServletRequestBuilder() {
        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.fileUpload("/design/1");
        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });
        return builder;
    }
}
