package com.developez.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        description = "Modello di dati per la creazione di un Commento"
)
public class CommentDto {
    private Long id;

    // Il nome non dovrebbe essere vuoto o null
    @Schema(
            description = "Nome del Commento"
    )
    @NotEmpty(message = "Il nome non può essere vuoto")
    private String name;

    // l'email non dovrebbe essere vuoto o null
    // validazione email
    @Schema(
            description = "Email del Commento"
    )
    @NotEmpty(message = "Email non dovrebbe essere vuoto")
    @Email
    private String email;

    // Il body non dovrebbe essere vuoto o null
    // Il body non dovrebbe essere più corto di 10 caratteri
    @Schema(
            description = "Body del Commento"
    )
    @NotEmpty(message = "Il body non può essere vuoto")
    @Size(min = 10, message = "Il body dovrebbe essere di almeno 10 caratteri")
    private String body;
}
