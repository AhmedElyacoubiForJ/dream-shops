package edu.yacoubi.dreamshops.dto.image;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImagePatchDTO {
    @NotNull(message = "Image ID cannot be null")
    private Long id;

    @Size(max = 100, message = "File name must not exceed 100 characters")
    private String fileName;

    @Size(max = 255, message = "Download URL must not exceed 255 characters")
    private String downloadUrl;
}
