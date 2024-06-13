package alura.backend.challenge.edicao1.controller;

import alura.backend.challenge.edicao1.domain.dto.categoria.CategoriaDTO;
import alura.backend.challenge.edicao1.domain.dto.categoria.DadosCadastroCategoriaDTO;
import alura.backend.challenge.edicao1.domain.dto.categoria.DadosDetalhadosCategoriaDTO;
import alura.backend.challenge.edicao1.domain.dto.categoria.DadosListagemCategoriaDTO;
import alura.backend.challenge.edicao1.domain.model.Categoria;
import alura.backend.challenge.edicao1.domain.repository.CategoriaRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class CategoriaControllerTest {



    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaController categoriaController;

    //Models
    CategoriaDTO categoriaDTO = new CategoriaDTO(
            1L,
            "DTO",
            "#f1f1f1"
    );
    Categoria categoria = new Categoria(categoriaDTO);

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
                () -> assertEquals(0, Objects.requireNonNull(responseEntity.getBody()).getTotalElements())
        );
    }

    @Test
    @DisplayName("Deve retornar detalhes da categoria se ela existir")
    void pesquisarCategoriaExistente() {
        // Dado
        Long categoriaId = 1L;
        when(categoriaRepository.existsById(categoriaId)).thenReturn(true);
        when(categoriaRepository.getReferenceById(categoriaId)).thenReturn(categoria);

        // Quando
        ResponseEntity<DadosDetalhadosCategoriaDTO> responseEntity = categoriaController.pesquisar(categoriaId);

        // Então
        assertAll(
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
                () -> assertNotNull(responseEntity.getBody()),
                () -> assertEquals(categoria.getId(), Objects.requireNonNull(responseEntity.getBody()).id()),
                () -> assertEquals(categoria.getTitulo(), Objects.requireNonNull(responseEntity.getBody()).titulo()),
                () -> assertEquals(categoria.getCor(), Objects.requireNonNull(responseEntity.getBody()).cor())
        );
    }

    @Test
    @DisplayName("Deve lançar exceção se categoria não existir")
    void pesquisarCategoriaNaoExistente() {
        // Dado
        Long categoriaId = 1L;
        when(categoriaRepository.existsById(categoriaId)).thenReturn(false);

        // Quando/Então
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            categoriaController.pesquisar(categoriaId);
        });

        assertEquals("Categoria não existente!", exception.getMessage());
    }

    @Test
    @DisplayName("Deve cadastrar uma nova categoria")
    void cadastrarCategoria() {
        // Dado
        DadosCadastroCategoriaDTO dados = new DadosCadastroCategoriaDTO("Categoria Teste", "Cor Teste");
        Categoria categoria = new Categoria(dados);
        categoria.setId(1L);

        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        // Quando
        ResponseEntity<?> responseEntity = categoriaController.cadastrar(dados, UriComponentsBuilder.newInstance());

        // Então
        assertAll(
                () -> assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode()),
                () -> assertNotNull(responseEntity.getBody()),
                () -> assertTrue(responseEntity.getBody() instanceof DadosDetalhadosCategoriaDTO),
                () -> assertEquals(categoria.getId(), ((DadosDetalhadosCategoriaDTO) Objects.requireNonNull(responseEntity.getBody())).id()),
                () -> assertEquals(categoria.getTitulo(), ((DadosDetalhadosCategoriaDTO) Objects.requireNonNull(responseEntity.getBody())).titulo())
        );

        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }
}