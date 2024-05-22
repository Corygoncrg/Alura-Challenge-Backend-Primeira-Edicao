package alura.backend.challenge.edicao1.domain.dto.categoria;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoCategoriaDTO
        (@NotNull Long id, String titulo, String cor) {
}
