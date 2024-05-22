package alura.backend.challenge.edicao1.domain.dto.categoria;

import alura.backend.challenge.edicao1.domain.model.Categoria;

public record DadosDetalhadosCategoriaDTO(Long id, String titulo, String cor) {

    public DadosDetalhadosCategoriaDTO(Categoria categoria) {
        this(categoria.getId(), categoria.getTitulo(), categoria.getCor());
    }
}
