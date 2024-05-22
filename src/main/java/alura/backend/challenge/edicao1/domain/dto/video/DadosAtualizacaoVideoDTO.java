package alura.backend.challenge.edicao1.domain.dto.video;

import alura.backend.challenge.edicao1.domain.model.Categoria;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoVideoDTO
        (@NotNull Long id, String descricao,
         String url,
         Categoria categoriaId)
{
}
