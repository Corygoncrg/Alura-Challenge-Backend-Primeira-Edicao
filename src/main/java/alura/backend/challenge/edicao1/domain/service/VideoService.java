package alura.backend.challenge.edicao1.domain.service;

import alura.backend.challenge.edicao1.domain.dto.video.DadosAtualizacaoVideoDTO;
import alura.backend.challenge.edicao1.domain.dto.video.DadosCadastroVideoDTO;
import alura.backend.challenge.edicao1.domain.dto.video.DadosDetalhadosVideoDTO;
import alura.backend.challenge.edicao1.domain.model.Categoria;
import alura.backend.challenge.edicao1.domain.model.Video;
import alura.backend.challenge.edicao1.domain.repository.CategoriaRepository;
import alura.backend.challenge.edicao1.domain.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class VideoService {

    @Autowired
    private VideoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaService categoriaService;

    public ResponseEntity<DadosDetalhadosVideoDTO> cadastrarComCategoria(DadosCadastroVideoDTO dados, UriComponentsBuilder uriBuilder) {
        var categoriaDTO = categoriaService.obterPorId(dados.categoria().getId());
        var categoria = new Categoria(categoriaDTO);
        var video = new Video(dados, categoria);
        repository.save(video);
        var uri = uriBuilder.path("/videos/{id}").buildAndExpand(video.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhadosVideoDTO(video));}

    public ResponseEntity<DadosDetalhadosVideoDTO> cadastrarSemCategoria(DadosCadastroVideoDTO dados, UriComponentsBuilder uriBuilder) {
        var categoria = categoriaRepository.getReferenceById(1L);
        var video = new Video(dados, categoria);
        repository.save(video);
        var uri = uriBuilder.path("/videos/{id}").buildAndExpand(video.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhadosVideoDTO(video));
    }

    public ResponseEntity<DadosDetalhadosVideoDTO> atualizar(DadosAtualizacaoVideoDTO dados) {
        var categoriaDTO = categoriaService.obterPorId(dados.categoria().getId());
        var categoria = new Categoria(categoriaDTO);
        var video = repository.getReferenceById(dados.id());
        video.atualizar(dados, categoria);

        return ResponseEntity.ok (new DadosDetalhadosVideoDTO(video));
    }
}
