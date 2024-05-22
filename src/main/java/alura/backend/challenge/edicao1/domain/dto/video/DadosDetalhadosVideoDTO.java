package alura.backend.challenge.edicao1.domain.dto.video;


import alura.backend.challenge.edicao1.domain.model.Categoria;
import alura.backend.challenge.edicao1.domain.model.Video;

public record DadosDetalhadosVideoDTO (Long id, String titulo, String descricao, String url, Categoria categoriaId){

    public DadosDetalhadosVideoDTO(Video video) {
        this(video.getId(), video.getTitulo(), video.getDescricao(), video.getUrl(), video.getCategoria());
    }

}
