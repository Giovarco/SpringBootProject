package net.mignemi.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DesignDto {
    private Long id;
    private String title;
    private List<Long> tags;
    private byte[] image;
}