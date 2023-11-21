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
        description = "Modello di dati per la ricezione di un token JWT"
)
public class JwtAuthResponse {
    @Schema(
            description = "Token JWT"
    )
    private String accessToken;

    @Schema(
            description = "Tipologia del token"
    )
    private String tokenType = "Bearer";
}
