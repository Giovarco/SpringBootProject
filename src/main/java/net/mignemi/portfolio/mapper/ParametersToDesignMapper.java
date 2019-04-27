package net.mignemi.portfolio.mapper;

import net.mignemi.portfolio.model.Design;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ParametersToDesignMapper {

    public static Design map(String title, MultipartFile file) {
        try {
            return Design.builder()
                    .title(title)
                    .image(file.getBytes())
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }
}
