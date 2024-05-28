package alura.backend.challenge.edicao1.controller;

import alura.backend.challenge.edicao1.domain.dto.categoria.DadosListagemCategoriaDTO;
import alura.backend.challenge.edicao1.domain.repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CategoriaControllerTest {


    private CategoriaRepository categoriaRepository;
    private CategoriaController categoriaController;

    @BeforeEach
    void setUp() {
        categoriaRepository = mock(CategoriaRepository.class);
        categoriaController = new CategoriaController();
    }

    @Test
    @DisplayName("Deve listar todas as categorias")
    void listarCategorias() {
        // Dado
        Pageable paginacao = Pageable.unpaged();
        when(categoriaRepository.findAll(paginacao)).thenReturn(new PageImpl<>(Collections.emptyList()));

        // Quando
        ResponseEntity<Page<DadosListagemCategoriaDTO>> responseEntity = categoriaController.listar(paginacao);

        // Então
        assertAll(
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
                () -> assertNotNull(responseEntity.getBody()),
                () -> assertEquals(0, responseEntity.getBody().getTotalElements())
        );
    }

    // Adicione mais testes para os outros métodos do controlador conforme necessário
}
