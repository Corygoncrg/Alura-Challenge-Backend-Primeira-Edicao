package alura.backend.challenge.edicao1.controller;

import alura.backend.challenge.edicao1.domain.dto.categoria.DadosAtualizacaoCategoriaDTO;
import alura.backend.challenge.edicao1.domain.dto.categoria.DadosCadastroCategoriaDTO;
import alura.backend.challenge.edicao1.domain.dto.categoria.DadosDetalhadosCategoriaDTO;
import alura.backend.challenge.edicao1.domain.dto.categoria.DadosListagemCategoriaDTO;
import alura.backend.challenge.edicao1.domain.dto.video.VideoDTO;
import alura.backend.challenge.edicao1.domain.model.Categoria;
import alura.backend.challenge.edicao1.domain.repository.CategoriaRepository;
import alura.backend.challenge.edicao1.domain.repository.VideoRepository;
import alura.backend.challenge.edicao1.domain.service.CategoriaService;
import alura.backend.challenge.edicao1.domain.service.VideoService;
import alura.backend.challenge.edicao1.infra.exception.ValidacaoException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoService videoService;

    @GetMapping
    public ResponseEntity<Page<DadosListagemCategoriaDTO>> listar(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {
        var page = repository.findAll(paginacao).map(DadosListagemCategoriaDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}/videos/")
        public ResponseEntity<List<VideoDTO>> listarVideosPorCategoria(@PathVariable @Valid Long id, @PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {
        var listaVideos = videoRepository.findByCategoria(id).stream().map(v -> new VideoDTO(v.getId(), v.getTitulo(),
                        v.getDescricao(), v.getUrl(), v.getAberto(), v.getCategoria()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaVideos);
    }

    @GetMapping("/{id}")
    public ResponseEntity pesquisar(@PathVariable @Valid Long id) {
        if (!repository.existsById(id)) {
            throw new ValidacaoException("Categoria não existente!");
        }
        Categoria categoria = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhadosCategoriaDTO(categoria));
    }

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroCategoriaDTO dados, UriComponentsBuilder uriBuilder) {
        var categoria = new Categoria(dados);

        repository.save(categoria);
        var uri = uriBuilder.path("/categorias/{id}").buildAndExpand(categoria.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhadosCategoriaDTO(categoria));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar (@RequestBody @Valid DadosAtualizacaoCategoriaDTO dados) {
    var categoria = repository.getReferenceById(dados.id());
    categoria.atualizar(dados);

    return ResponseEntity.ok(new DadosDetalhadosCategoriaDTO(categoria));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar (@PathVariable @Valid Long id) {
        var categoria = repository.getReferenceById(id);
        repository.delete(categoria);
        return ResponseEntity.noContent().build();
        }
}
