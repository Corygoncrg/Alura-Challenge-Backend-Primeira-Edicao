package alura.backend.challenge.edicao1.controller;

import alura.backend.challenge.edicao1.domain.dto.categoria.CategoriaDTO;
import alura.backend.challenge.edicao1.domain.dto.video.DadosAtualizacaoVideoDTO;
import alura.backend.challenge.edicao1.domain.dto.video.DadosCadastroVideoDTO;
import alura.backend.challenge.edicao1.domain.dto.video.DadosDetalhadosVideoDTO;
import alura.backend.challenge.edicao1.domain.dto.video.DadosListagemVideoDTO;
import alura.backend.challenge.edicao1.domain.model.Categoria;
import alura.backend.challenge.edicao1.domain.model.Video;
import alura.backend.challenge.edicao1.domain.repository.CategoriaRepository;
import alura.backend.challenge.edicao1.domain.repository.VideoRepository;
import alura.backend.challenge.edicao1.domain.service.CategoriaService;
import alura.backend.challenge.edicao1.domain.service.VideoService;
import alura.backend.challenge.edicao1.infra.exception.ValidacaoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class VideoControllerTest {

    @InjectMocks
    private VideoController videoController;

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private CategoriaService categoriaService;

    @Mock
    private VideoService videoService;

    //Models
    CategoriaDTO categoriaDTO = new CategoriaDTO(
            1L,
            "DTO",
            "#f1f1f1"
    );
    Categoria categoria = new Categoria(categoriaDTO);

    DadosCadastroVideoDTO dados = new DadosCadastroVideoDTO(
            "DTO",
            "descricaoDTO",
            "urlDTO",
            categoria);

    Video video = new Video(dados, categoria);

    @Test
    @DisplayName("Deve cadastrar um vídeo com categoria")
    void cadastrarVideoComCategoria() {
        when(videoService.cadastrarComCategoria(any(DadosCadastroVideoDTO.class), any(UriComponentsBuilder.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(new DadosDetalhadosVideoDTO(new Video(dados, categoria))));

        var responseEntity = videoController.cadastrar(dados, UriComponentsBuilder.newInstance());

        assertAll(
                () -> assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode()),
                () -> assertNotNull(responseEntity.getBody()),
                () -> assertInstanceOf(DadosDetalhadosVideoDTO.class, responseEntity.getBody())
        );
    }

    @Test
    @DisplayName("Deve cadastrar um vídeo sem categoria")
    void cadastrarVideoSemCategoria() {
        DadosCadastroVideoDTO dados = new DadosCadastroVideoDTO("Título", "Descrição", "URL", null);
        when(videoService.cadastrarSemCategoria(any(DadosCadastroVideoDTO.class), any(UriComponentsBuilder.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(new DadosDetalhadosVideoDTO(new Video(dados, categoria))));

        ResponseEntity<DadosDetalhadosVideoDTO> responseEntity = videoController.cadastrar(dados, UriComponentsBuilder.newInstance());

        assertAll(
                () -> assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode()),
                () -> assertNotNull(responseEntity.getBody())
        );
    }

    @Test
    @DisplayName("Deve listar vídeos por título")
    void listarVideosPorTitulo() {
        Pageable paginacao = Pageable.unpaged();
        String titulo = "Teste";
        when(videoRepository.findAllByTituloContaining(titulo, paginacao)).thenReturn(new PageImpl<>(Collections.emptyList()));

        ResponseEntity<Page<DadosListagemVideoDTO>> responseEntity = videoController.listar(titulo, paginacao);

        assertAll(
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
                () -> assertNotNull(responseEntity.getBody()),
                () -> assertEquals(0, Objects.requireNonNull(responseEntity.getBody()).getTotalElements())
        );
        verify(videoRepository, times(1)).findAllByTituloContaining(titulo, paginacao);
    }
    @Test
    @DisplayName("Deve lançar exceção se vídeo não for encontrado")
    void pesquisarVideoNaoEncontrado() {
        Long videoId = 1L;
        when(videoRepository.existsById(anyLong())).thenReturn(false);

        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> videoController.pesquisarPorId(videoId));

        assertEquals("Video não encontrado devido a id inválido!", exception.getMessage());
        verify(videoRepository, times(1)).existsById(videoId);
    }

    @Test
    @DisplayName("Deve retornar vídeo detalhado por ID")
    void pesquisarVideoPorId() {
        Long videoId = 1L;
        video.setId(1L);
        when(videoRepository.existsById(anyLong())).thenReturn(true);
        when(videoRepository.getReferenceById(anyLong())).thenReturn(video);

        ResponseEntity<?> responseEntity = videoController.pesquisarPorId(videoId);

        assertAll(
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
                () -> assertNotNull(responseEntity.getBody()),
                () -> assertInstanceOf(DadosDetalhadosVideoDTO.class, responseEntity.getBody()),
                () -> assertEquals(videoId, ((DadosDetalhadosVideoDTO) Objects.requireNonNull(responseEntity.getBody())).id())
        );
        verify(videoRepository, times(1)).existsById(videoId);
        verify(videoRepository, times(1)).getReferenceById(videoId);
    }

    @Test
    @DisplayName("Deve listar os 5 primeiros vídeos gratuitos")
    void listarVideosGratis() {
        Pageable paginacao = Pageable.unpaged();
        when(videoRepository.findTop5ByOrderByIdAsc(paginacao)).thenReturn(new PageImpl<>(Collections.emptyList()));

        ResponseEntity<Page<DadosListagemVideoDTO>> responseEntity = videoController.listarVideosGratis(paginacao);

        assertAll(
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
                () -> assertNotNull(responseEntity.getBody()),
                () -> assertEquals(0, Objects.requireNonNull(responseEntity.getBody()).getTotalElements())
        );
        verify(videoRepository, times(1)).findTop5ByOrderByIdAsc(paginacao);
    }

    @Test
    @DisplayName("Deve atualizar um vídeo existente")
    void atualizarVideo() {
        Long videoId = 1L;
        categoria.setId(1L);
        video.setId(videoId);
        when(videoService.atualizar(any(DadosAtualizacaoVideoDTO.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(new DadosDetalhadosVideoDTO(video)));

        DadosAtualizacaoVideoDTO dados = new DadosAtualizacaoVideoDTO(videoId, "Descrição Atualizada", "URL Atualizada", categoria);

        ResponseEntity<?> responseEntity = videoController.atualizar(dados);

        assertAll(
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
                () -> assertNotNull(responseEntity.getBody()),
                () -> assertInstanceOf(DadosDetalhadosVideoDTO.class, responseEntity.getBody()),
                () -> assertEquals(videoId, ((DadosDetalhadosVideoDTO) Objects.requireNonNull(responseEntity.getBody())).id())
        );
        verify(videoService, times(1)).atualizar(dados);
    }

    @Test
    @DisplayName("Deve inativar um vídeo existente")
    void inativarVideo() {
        Long videoId = 1L;
        video.setId(videoId);
        when(videoRepository.getReferenceById(anyLong())).thenReturn(video);

        ResponseEntity<?> responseEntity = videoController.fechar(videoId);

        verify(videoRepository, times(1)).getReferenceById(videoId);
        assertAll(
                () -> assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode())
        );
    }
}
