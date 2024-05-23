package alura.backend.challenge.edicao1.domain.model;


import alura.backend.challenge.edicao1.domain.dto.categoria.CategoriaDTO;
import alura.backend.challenge.edicao1.domain.dto.categoria.DadosAtualizacaoCategoriaDTO;
import alura.backend.challenge.edicao1.domain.dto.categoria.DadosCadastroCategoriaDTO;
import alura.backend.challenge.edicao1.domain.dto.categoria.DadosCategoria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "categorias")
@Entity(name = "Categoria")
@Getter
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
    private List<Video> videos = new ArrayList<>();

    public Categoria(DadosCadastroCategoriaDTO dados) {
        this.titulo = dados.titulo();
        this.cor = dados.cor();

    }

    public Categoria(DadosCategoria dados) {
        this.id = dados.id();
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

//    public void inativar() {
//        this.ativa = false;
//    }
}
