package com.developez.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "Modello di dati per la ricezione di una lista di Post"
)
public class PostResponse {
    @Schema(
            description = "Lista di Post"
    )
    private List<PostDto> content;
    @Schema(
            description = "Pagina corrente"
    )
    private int pageNo;
    @Schema(
            description = "Numero di elementi per pagina"
    )
    private int pageSize;
    @Schema(
            description = "Numero totale di elementi"
    )
    private Long totalElements;
    @Schema(
            description = "Numero totale di pagine"
    )
    private int totalPages;
    @Schema(
            description = "Ultima pagina"
    )
    private boolean last;
}
