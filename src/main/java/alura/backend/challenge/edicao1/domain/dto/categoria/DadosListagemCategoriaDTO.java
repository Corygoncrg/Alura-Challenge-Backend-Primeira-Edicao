package alura.backend.challenge.edicao1.domain.dto.categoria;

import alura.backend.challenge.edicao1.domain.model.Categoria;

public record DadosListagemCategoriaDTO(Long id, String titulo, String cor)
{
    public DadosListagemCategoriaDTO(Categoria categoria) {
    this(categoria.getId(), categoria.getTitulo(), categoria.getCor());
    }
}
