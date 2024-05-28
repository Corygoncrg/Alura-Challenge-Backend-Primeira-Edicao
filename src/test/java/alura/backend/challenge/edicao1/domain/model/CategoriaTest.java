package alura.backend.challenge.edicao1.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import alura.backend.challenge.edicao1.domain.dto.categoria.CategoriaDTO;
import alura.backend.challenge.edicao1.domain.dto.categoria.DadosAtualizacaoCategoriaDTO;
import alura.backend.challenge.edicao1.domain.dto.categoria.DadosCadastroCategoriaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CategoriaTest {

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
    }

    @Test
    @DisplayName("Deve atualizar título e cor da categoria")
    void atualizarTituloECor() {
        // Dado
        DadosAtualizacaoCategoriaDTO dados = new DadosAtualizacaoCategoriaDTO(null,"Novo título", "nova-cor");

        // Quando
        categoria.atualizar(dados);

        // Então
        assertEquals("Novo título", categoria.getTitulo());
        assertEquals("nova-cor", categoria.getCor());
    }

    @Test
    @DisplayName("Não deve atualizar título e cor da categoria se dados forem nulos")
    void naoAtualizarTituloECorSeDadosForemNulos() {
        // Dado
        DadosAtualizacaoCategoriaDTO dados = new DadosAtualizacaoCategoriaDTO(null, null, null);

        // Quando
        categoria.atualizar(dados);

        // Então
        assertNull(categoria.getTitulo());
        assertNull(categoria.getCor());
    }

    @Test
    @DisplayName("Deve construir uma categoria corretamente a partir de DadosCadastroCategoriaDTO")
    void construirCategoriaCorretamenteAPartirDeDadosCadastroCategoriaDTO() {
        // Dado
        DadosCadastroCategoriaDTO dados = new DadosCadastroCategoriaDTO("Título", "Cor");

        // Quando
        Categoria categoria = new Categoria(dados);

        // Então
        assertEquals("Título", categoria.getTitulo());
        assertEquals("Cor", categoria.getCor());
        assertNotNull(categoria.getVideos());
    }


    @Test
    @DisplayName("Deve construir uma categoria corretamente a partir de CategoriaDTO")
    void construirCategoriaCorretamenteAPartirDeCategoriaDTO() {
        // Dado
        CategoriaDTO categoriaDTO = new CategoriaDTO(1L, "Título", "Cor");

        // Quando
        Categoria categoria = new Categoria(categoriaDTO);

        // Então
        assertEquals(1L, categoria.getId());
        assertEquals("Título", categoria.getTitulo());
        assertEquals("Cor", categoria.getCor());
        assertNotNull(categoria.getVideos());
    }
}
