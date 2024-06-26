package alura.backend.challenge.edicao1.domain.model;


import alura.backend.challenge.edicao1.domain.dto.categoria.CategoriaDTO;
import alura.backend.challenge.edicao1.domain.dto.categoria.DadosAtualizacaoCategoriaDTO;
import alura.backend.challenge.edicao1.domain.dto.categoria.DadosCadastroCategoriaDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "categorias")
@Entity(name = "Categoria")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String cor;
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonBackReference
    private List<Video> videos = new ArrayList<>();

    public Categoria(DadosCadastroCategoriaDTO dados) {
        this.titulo = dados.titulo();
        this.cor = dados.cor();

    }


    public Categoria(CategoriaDTO categoria) {
        this.id = categoria.id();
        this.titulo = categoria.titulo();
        this.cor = categoria.cor();
    }

    public void atualizar(DadosAtualizacaoCategoriaDTO dados) {
        if (dados.titulo() != null){
            this.titulo = dados.titulo();
        }
        if (dados.cor() != null) {
            this.cor = dados.cor();
        }

    }
    public Categoria(int id) {
        this.id = (long) id;
    }

    public void setId(long l) {
    }
}
