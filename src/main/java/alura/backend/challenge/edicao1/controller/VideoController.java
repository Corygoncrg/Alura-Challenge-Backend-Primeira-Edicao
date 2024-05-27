package alura.backend.challenge.edicao1.controller;


import alura.backend.challenge.edicao1.domain.dto.video.DadosAtualizacaoVideoDTO;
import alura.backend.challenge.edicao1.domain.dto.video.DadosCadastroVideoDTO;
import alura.backend.challenge.edicao1.domain.dto.video.DadosDetalhadosVideoDTO;
import alura.backend.challenge.edicao1.domain.dto.video.DadosListagemVideoDTO;
import alura.backend.challenge.edicao1.domain.model.Categoria;
import alura.backend.challenge.edicao1.domain.model.Video;
import alura.backend.challenge.edicao1.domain.repository.CategoriaRepository;
import alura.backend.challenge.edicao1.domain.repository.VideoRepository;
import alura.backend.challenge.edicao1.domain.service.CategoriaService;
import alura.backend.challenge.edicao1.infra.exception.ValidacaoException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("videos")
public class VideoController {

    @Autowired
    private VideoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    CategoriaService categoriaService;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar (@RequestBody @Valid DadosCadastroVideoDTO dados, UriComponentsBuilder uriBuilder) {
        if (dados.categoria() != null) {
            var categoriaDTO = categoriaService.obterPorId(dados.categoria().getId());
            var categoria = new Categoria(categoriaDTO);
//        var categoria = categoriaRepository.findById(dados.categoria()).orElseThrow(() -> new ValidacaoException("Categoria não encontrada!"));
        var video = new Video(dados, categoria);
        repository.save(video);
        var uri = uriBuilder.path("/videos/{id}").buildAndExpand(video.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhadosVideoDTO(video));
        }
        else {
            var categoria = categoriaRepository.getReferenceById(1L);
            var video = new Video(dados, categoria);
            repository.save(video);
            var uri = uriBuilder.path("/videos/{id}").buildAndExpand(video.getId()).toUri();
            return ResponseEntity.created(uri).body(new DadosDetalhadosVideoDTO(video));
        }
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemVideoDTO>> listar (@RequestParam(value = "search",required = false) String titulo,
                                                               @PageableDefault (size = 10, sort = {"id"}) Pageable paginacao) {
        if (titulo == null) {
            var page = repository.findAllByAbertoTrue(paginacao).map(DadosListagemVideoDTO::new);
            return ResponseEntity.ok(page);
        } else {
            var video = repository.findAllByTituloContaining(titulo, paginacao).map(DadosListagemVideoDTO::new);
            return ResponseEntity.ok(video);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity pesquisarPorId ( @PathVariable @Valid Long id ) {
        if (!repository.existsById(id)) {
            throw new ValidacaoException("Video não encontrado devido a id inválido!");
        }
        Video video = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhadosVideoDTO(video));
    }


    @PutMapping
    @Transactional
    public ResponseEntity atualizar (@RequestBody @Valid DadosAtualizacaoVideoDTO dados) {
        var categoriaDTO = categoriaService.obterPorId(dados.categoria().getId());
        var categoria = new Categoria(categoriaDTO);
        System.out.println(categoria);
        var video = repository.getReferenceById(dados.id());
        video.atualizar(dados, categoria);

        return ResponseEntity.ok (new DadosDetalhadosVideoDTO(video));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity fechar (@PathVariable @Valid Long id){
        var video = repository.getReferenceById(id);
        video.inativarVideo();

        return ResponseEntity.noContent().build();
    }
}
