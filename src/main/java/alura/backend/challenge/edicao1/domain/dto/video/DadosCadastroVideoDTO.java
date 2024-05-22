package alura.backend.challenge.edicao1.domain.dto.video;

import alura.backend.challenge.edicao1.domain.model.Categoria;
import jakarta.validation.constraints.NotBlank;

public record DadosCadastroVideoDTO
(
    @NotBlank String titulo,
    @NotBlank String descricao,
    @NotBlank String url,
     Categoria categoria
    ) {
}
