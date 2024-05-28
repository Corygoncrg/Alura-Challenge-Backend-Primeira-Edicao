package alura.backend.challenge.edicao1.controller;

import alura.backend.challenge.edicao1.domain.dto.categoria.DadosCadastroCategoriaDTO;
import alura.backend.challenge.edicao1.domain.dto.categoria.DadosDetalhadosCategoriaDTO;
import alura.backend.challenge.edicao1.domain.model.Categoria;
import alura.backend.challenge.edicao1.domain.repository.CategoriaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class CategoriaControllerTestIT {
    @Autowired
    MockMvc mvc;
    @Autowired
    JacksonTester<DadosCadastroCategoriaDTO> cadastroJson;

    @Autowired
    JacksonTester<DadosDetalhadosCategoriaDTO> detalhadosJson;

    @MockBean
    CategoriaRepository categoriaRepository;

    @Test
    @DisplayName("Deveria devolver erro 400 quando informações invalidas")
    void cadastrarCenario1() throws Exception {
        var response = mvc.perform(post("/categorias")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver erro 201 quando informações validas")
    void cadastrarCenario2() throws Exception {
        var dadosCategoria = new DadosCadastroCategoriaDTO("titulo", "#f1f2f2");

        when(categoriaRepository.save(any())).thenReturn(new Categoria(dadosCategoria));

        var response = mvc.perform(post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cadastroJson.write(dadosCategoria).getJson()))
                .andReturn().getResponse();

        var dadosDetalhados = new DadosDetalhadosCategoriaDTO(
                null,
                dadosCategoria.titulo(),
                dadosCategoria.cor()
        );
        var jsonEsperado = detalhadosJson.write(dadosDetalhados).getJson();


        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}