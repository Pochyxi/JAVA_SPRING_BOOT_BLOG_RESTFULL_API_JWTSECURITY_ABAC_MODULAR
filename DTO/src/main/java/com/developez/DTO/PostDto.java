package com.developez.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Schema(
        description = "Modello di dati per la creazione di un Post"
)
public class PostDto {
    private Long id;

    // Il titolo non deve essere null o essere vuoto
    // Il titolo non deve essere più corto di 2 caratteri
    @Schema(
            description = "Titolo del Post"
    )
    @NotEmpty
    @Size(min = 2, message = "Il titolo del Post dovrebbe essere di almeno 2 caratteri")
    private String title;

    // La descrizione non deve essere null o essere vuota
    // La descrizione non deve essere più corta di 10 caratteri
    @Schema(
            description = "Descrizione del Post"
    )
    @NotEmpty
    @Size(min = 10, message = "La descrizione del Post dovrebbe essere di almeno 10 caratteri")
    private String description;

    // Il contenuto non deve essere null o essere vuoto
    @NotEmpty
    @Schema(
            description = "Contenuto del Post"
    )
    private String content;

    private Set<CommentDto> comments;

    @Schema(
            description = "Categoria del Post"
    )
    private Long categoryId;
}
