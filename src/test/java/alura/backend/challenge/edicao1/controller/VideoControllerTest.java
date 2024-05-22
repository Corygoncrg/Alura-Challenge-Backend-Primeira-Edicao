//package alura.backend.challenge.edicao1.controller;
//
//import alura.backend.challenge.edicao1.domain.dto.video.DadosCadastroVideoDTO;
//import alura.backend.challenge.edicao1.domain.dto.video.DadosDetalhadosVideoDTO;
//import alura.backend.challenge.edicao1.domain.model.Video;
//import alura.backend.challenge.edicao1.domain.repository.VideoRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.json.JacksonTester;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@AutoConfigureJsonTesters
//class VideoControllerTest {
//    @Autowired
//    MockMvc mvc;
//    @Autowired
//    JacksonTester<DadosCadastroVideoDTO> cadastroJson;
//
//    @Autowired
//    JacksonTester<DadosDetalhadosVideoDTO> detalhadosJson;
//
//@MockBean
//    VideoRepository repository;
//
//    @Test
//    @DisplayName("Deveria devolver erro 400 quando informacoes estao invalidas")
//    void cadastrarCenario1() throws Exception {
//        var response = mvc.perform(post("/videos")).andReturn().getResponse();
//
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
//    }
//
//    @Test
//    @DisplayName("Deveria devolver erro 201 quando informacoes estao validas")
//    void cadastrarCenario2() throws Exception {
//        var cadastroVideo = new DadosCadastroVideoDTO("titulo", "descricao", "youtube.com.br");
//
//        when(repository.save(any())).thenReturn(new Video(cadastroVideo));
//
//        var response = mvc.perform(post("/videos")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(cadastroJson.write(cadastroVideo).getJson()))
//                .andReturn().getResponse();
//
//        var dadosDetalhados = new DadosDetalhadosVideoDTO(
//                null,
//                cadastroVideo.titulo(),
//                cadastroVideo.descricao(),
//                cadastroVideo.url()
//        );
//
//        var jsonEsperado = detalhadosJson.write(dadosDetalhados).getJson();
//
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
//        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
//    }
//}