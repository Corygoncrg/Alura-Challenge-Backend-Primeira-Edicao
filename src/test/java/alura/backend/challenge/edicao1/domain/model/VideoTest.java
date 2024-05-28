package alura.backend.challenge.edicao1.domain.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import alura.backend.challenge.edicao1.domain.dto.video.DadosAtualizacaoVideoDTO;
import alura.backend.challenge.edicao1.domain.dto.video.DadosCadastroVideoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VideoTest {

    private Video video;
    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = mock(Categoria.class);
        video = new Video();
    }

    @Test
    @DisplayName("Deve atualizar descrição e URL do vídeo")
    void atualizarDescricaoEUrl() {
        // Dado
        DadosAtualizacaoVideoDTO dados = new DadosAtualizacaoVideoDTO(null,"Nova descrição", "nova-url", null);

        // Quando
        video.atualizar(dados, categoria);

        // Então
        assertEquals("Nova descrição", video.getDescricao());
        assertEquals("nova-url", video.getUrl());
    }

    @Test
    @DisplayName("Não deve atualizar categoria se dadosCategoria for nulo")
    void naoAtualizarCategoriaSeDadosCategoriaForNulo() {
        // Dado
        DadosAtualizacaoVideoDTO dados = new DadosAtualizacaoVideoDTO(null, null, null, null);

        // Quando
        video.atualizar(dados, null);

        // Então
        assertNull(video.getCategoria());
    }

    @Test
    @DisplayName("Deve inativar o vídeo")
    void inativarVideo() {
        // Quando
        video.inativarVideo();

        // Então
        assertFalse(video.getAberto());
    }

    @Test
    @DisplayName("Deve construir um vídeo corretamente")
    void construirVideoCorretamente() {
        // Dado
        DadosCadastroVideoDTO dadosCadastroVideoDTO = new DadosCadastroVideoDTO("Título", "Descrição", "URL", categoria);

        // Quando
        Video video = new Video(dadosCadastroVideoDTO, categoria);

        // Então
        assertEquals("Título", video.getTitulo());
        assertEquals("Descrição", video.getDescricao());
        assertEquals("URL", video.getUrl());
        assertTrue(video.getAberto());
        assertEquals(categoria, video.getCategoria());
        verify(categoria).getVideos();
    }
}
