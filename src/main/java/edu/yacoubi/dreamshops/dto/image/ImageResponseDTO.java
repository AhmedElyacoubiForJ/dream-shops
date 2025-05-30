package edu.yacoubi.dreamshops.dto.image;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageResponseDTO {
    private Long id;
    private String fileName;
    private String downloadUrl;
}
