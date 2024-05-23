package alura.backend.challenge.edicao1.domain.model;

import alura.backend.challenge.edicao1.domain.dto.video.DadosAtualizacaoVideoDTO;
import alura.backend.challenge.edicao1.domain.dto.video.DadosCadastroVideoDTO;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "videos")
@Entity(name = "Video")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    private String url;
    private Boolean aberto;
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    public Video(DadosCadastroVideoDTO dados, Categoria categoria) {
        this.titulo = dados.titulo();
        this.descricao = dados.descricao();
        this.url = dados.url();
        this.aberto = true;
        this.categoria = categoria;
        categoria.getVideos().add(this);
    }


    public void atualizar(DadosAtualizacaoVideoDTO dados) {
        if (dados.descricao() != null){
            this.descricao = dados.descricao();
        }
        if (dados.url() != null) {
            this.url = dados.url();
        }
        if (dados.categoriaId() != null) {
            this.categoria = dados.categoriaId();
        }

    }

    public void inativarVideo() {
        this.aberto = false;
    }
}
