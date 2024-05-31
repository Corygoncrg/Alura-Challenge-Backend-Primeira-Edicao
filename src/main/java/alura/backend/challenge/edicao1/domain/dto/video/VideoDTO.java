package alura.backend.challenge.edicao1.domain.dto.video;

import alura.backend.challenge.edicao1.domain.model.Categoria;

public record VideoDTO(Long id, String titulo, String descricao, String url, Boolean aberto, Categoria categoria) {
}



