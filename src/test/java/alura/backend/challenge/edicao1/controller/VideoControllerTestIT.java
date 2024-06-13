package alura.backend.challenge.edicao1.controller;

import alura.backend.challenge.edicao1.domain.dto.categoria.CategoriaDTO;
import alura.backend.challenge.edicao1.domain.dto.video.DadosAtualizacaoVideoDTO;
import alura.backend.challenge.edicao1.domain.dto.video.DadosCadastroVideoDTO;
import alura.backend.challenge.edicao1.domain.dto.video.DadosDetalhadosVideoDTO;
import alura.backend.challenge.edicao1.domain.model.Categoria;
import alura.backend.challenge.edicao1.domain.model.Video;
import alura.backend.challenge.edicao1.domain.repository.CategoriaRepository;
import alura.backend.challenge.edicao1.domain.repository.VideoRepository;
import alura.backend.challenge.edicao1.domain.service.CategoriaService;
import alura.backend.challenge.edicao1.domain.service.VideoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureJsonTesters
@Transactional
class VideoControllerTestIT {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    JacksonTester<DadosCadastroVideoDTO> cadastroJson;

    @Autowired
    JacksonTester<DadosDetalhadosVideoDTO> detalhadosJson;

    @Autowired
    JacksonTester<DadosAtualizacaoVideoDTO> atualizadoJson;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private VideoService videoService;

    @Autowired
    private CategoriaService categoriaService;

    @BeforeEach
    void setUp() {
        videoRepository.deleteAll();
    }
    //Models
    CategoriaDTO categoriaDTO = new CategoriaDTO(
            1L,
            "DTO",
            "#f1f1f1"
    );
    Categoria categoria = new Categoria(categoriaDTO);

    DadosCadastroVideoDTO dadoscadastro = new DadosCadastroVideoDTO(
            "Titulo 1",
            "descricao 1",
            "url 1",
            categoria);
    DadosCadastroVideoDTO dadoscadastro2 = new DadosCadastroVideoDTO(
            "Titulo 2",
            "descricao 2",
            "url 2",
            categoria);

    Video video = new Video(dadoscadastro, categoria);
    Video video2 = new Video(dadoscadastro2, categoria);

    @Test
    @DisplayName("Deve devolver erro 400 quando informações invalidas")
    @WithMockUser
    void cadastrarCenario1() throws Exception {
        var response = mvc.perform(post("/videos")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver código HTTP 201 quando as informações estão válidas")
    @WithMockUser
    void cadastrarVideo() throws Exception {
        // Convertendo o DTO de cadastro para JSON
        String cadastroJson = objectMapper.writeValueAsString(dadoscadastro);

        // Executando a requisição POST
        MvcResult result = mvc.perform(post("/videos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cadastroJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        // Verificando a resposta
        String jsonResponse = result.getResponse().getContentAsString();
        DadosDetalhadosVideoDTO responseDto = objectMapper.readValue(jsonResponse, DadosDetalhadosVideoDTO.class);

        // Asserções
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(responseDto.titulo()).isEqualTo(dadoscadastro.titulo());
        assertThat(responseDto.descricao()).isEqualTo(dadoscadastro.descricao());
        assertThat(responseDto.url()).isEqualTo(dadoscadastro.url());
        assertThat(responseDto.categoriaId()).isEqualTo(dadoscadastro.categoria().getId());
    }

    @Test
    @DisplayName("Deve listar todos os vídeos")
    @WithMockUser
    void listarVideos() throws Exception {


        videoRepository.save(video);
        videoRepository.save(video2);

        var response = mvc.perform(get("/videos"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse();

        // Então
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        String jsonResponse = response.getContentAsString();
        assertThat(jsonResponse).contains(video.getTitulo(),video.getDescricao(),video.getUrl());
        assertThat(jsonResponse).contains(video2.getTitulo(),video2.getDescricao(),video2.getUrl());


    }

    @Test
    @DisplayName("Deve pesquisar vídeo por ID")
    @WithMockUser

    void pesquisarVideoPorId() throws Exception {
        // Dado
        videoRepository.save(video);
        videoRepository.save(video2);
        Long videoId = video2.getId();

        // Quando/Então
        var response = mvc.perform(get("/videos/" + videoId))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        String jsonResponse = response.getContentAsString();
        assertThat(jsonResponse).contains(video2.getTitulo(),video2.getDescricao(),video2.getUrl());
    }

    @Test
    @DisplayName("Deve atualizar um vídeo")
    @WithMockUser
    @Transactional
    void atualizarVideo() throws Exception {
        // Dado
        categoriaRepository.save(categoria);
        videoRepository.save(video);
        var dadosAtualizadosDTO = new DadosAtualizacaoVideoDTO(video.getId(), "Descrição Atualizada", "URL Atualizada", categoria);
        // Quando/Então
        var response = mvc.perform(put("/videos")
                        .content(atualizadoJson.write(dadosAtualizadosDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        String jsonResponse = response.getContentAsString();
        // Então
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonResponse);

    }
    @Test
    @DisplayName("Deve Inativar um vídeo")
    @WithMockUser

    void deletarVideo() throws Exception {
        // Dado
        videoRepository.save(video);
        Long videoId = video.getId();

        // Quando/Então
        mvc.perform(delete("/videos/" + videoId))
                .andExpect(status().isNoContent());
    }
}