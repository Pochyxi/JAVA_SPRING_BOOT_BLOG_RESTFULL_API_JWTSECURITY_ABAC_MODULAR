package com.developez.security.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "Modello di dati per la creazione di un nuovo utente"
)
public class SignupDto {

    @Schema(
            description = "Nome dell'utente"
    )
    private String name;
    @Schema(
            description = "Username dell'utente"
    )
    private String username;
    @Schema(
            description = "Email dell'utente"
    )
    private String email;
    @Schema(
            description = "Password dell'utente"
    )
    private String password;
}
