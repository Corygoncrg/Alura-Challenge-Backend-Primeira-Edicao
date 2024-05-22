package alura.backend.challenge.edicao1.domain.dto.video;


import alura.backend.challenge.edicao1.domain.model.Video;

public record DadosListagemVideoDTO(Long id, Long categoriaid, String titulo, String descricao, String url ) {
    public DadosListagemVideoDTO(Video video) {
        this(video.getId(), video.getCategoria().getId(),video.getTitulo(), video.getDescricao(), video.getUrl());
    }
}
