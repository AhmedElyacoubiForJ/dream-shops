package edu.yacoubi.dreamshops.dto.image;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageCreateDTO {
    @NotBlank(message = "Image file name cannot be blank")
    @Size(max = 100, message = "File name must not exceed 100 characters")
    private String fileName;

    @NotBlank(message = "Image URL cannot be blank")
    @Size(max = 255, message = "Download URL must not exceed 255 characters")
    private String downloadUrl;
}
