package alura.backend.challenge.edicao1.domain.model;

import alura.backend.challenge.edicao1.domain.dto.video.DadosAtualizacaoVideoDTO;
import alura.backend.challenge.edicao1.domain.dto.video.DadosCadastroVideoDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @JsonManagedReference

    private Categoria categoria;

    public Video(DadosCadastroVideoDTO dados, Categoria categoria) {
        this.titulo = dados.titulo();
        this.descricao = dados.descricao();
        this.url = dados.url();
        this.aberto = true;
        this.categoria = categoria;
        categoria.getVideos().add(this);
    }


    public void atualizar(DadosAtualizacaoVideoDTO dados, Categoria dadosCategoria) {
        if (dados.descricao() != null){
            this.descricao = dados.descricao();
        }
        if (dados.url() != null) {
            this.url = dados.url();
        }
        if (dados.categoria() != null) {
            this.categoria = dadosCategoria;
        }

    }

    public void inativarVideo() {
        this.aberto = false;
    }
}
