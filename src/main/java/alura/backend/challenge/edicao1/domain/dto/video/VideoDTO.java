package alura.backend.challenge.edicao1.domain.dto.video;

import alura.backend.challenge.edicao1.domain.model.Categoria;
import alura.backend.challenge.edicao1.domain.model.Video;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private String url;
    private Long categoriaId;

    public VideoDTO(Long id, String titulo, String descricao, String url, Boolean aberto, Categoria categoria) {

    }


}
