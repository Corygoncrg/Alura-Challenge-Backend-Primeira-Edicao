package alura.backend.challenge.edicao1.controller;

import alura.backend.challenge.edicao1.domain.dto.video.DadosCadastroVideoDTO;
import alura.backend.challenge.edicao1.domain.model.Categoria;
import alura.backend.challenge.edicao1.domain.repository.CategoriaRepository;
import alura.backend.challenge.edicao1.domain.repository.VideoRepository;
import alura.backend.challenge.edicao1.domain.service.CategoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VideoControllerTest {

    private VideoRepository videoRepository;
    private CategoriaRepository categoriaRepository;
    private CategoriaService categoriaService;
    private VideoController videoController;

    @BeforeEach
    void setUp() {
        videoRepository = mock(VideoRepository.class);
        categoriaRepository = mock(CategoriaRepository.class);
        categoriaService = mock(CategoriaService.class);
        videoController = new VideoController();
    }

    @Test
    @DisplayName("Deve cadastrar um vídeo sem categoria")
    void cadastrarVideoSemCategoria() {
        // Dado
        DadosCadastroVideoDTO dados = new DadosCadastroVideoDTO("Título", "Descrição", "URL", null);
        when(categoriaRepository.getReferenceById(anyLong())).thenReturn(new Categoria()); // Simulando categoria padrão

        // Quando
        ResponseEntity responseEntity = videoController.cadastrar(dados, UriComponentsBuilder.newInstance());

        // Então
        assertAll(
                () -> assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode()),
                () -> assertNotNull(responseEntity.getBody())
                // Verifique outros aspectos da resposta conforme necessário
        );
    }

    // Adicione mais testes para os outros métodos do controlador conforme necessário
}
