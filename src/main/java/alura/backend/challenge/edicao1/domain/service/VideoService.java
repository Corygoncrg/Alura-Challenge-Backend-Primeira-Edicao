package alura.backend.challenge.edicao1.domain.service;

import alura.backend.challenge.edicao1.domain.dto.video.VideoDTO;
import alura.backend.challenge.edicao1.domain.model.Video;
import alura.backend.challenge.edicao1.domain.repository.CategoriaRepository;
import alura.backend.challenge.edicao1.domain.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VideoService {

    @Autowired
    private VideoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;
    public VideoDTO obterPorId(Long id) {
        Optional<Video> categoria = repository.findById(id);
        if (categoria.isPresent()) {
            Video v = categoria.get();
            return new VideoDTO(v.getId(), v.getTitulo(),
                    v.getDescricao(), v.getUrl(), v.getAberto(), v.getCategoria());
        }
        return null;
    }

    public List<VideoDTO> obterPorCategoria(Long id, Pageable paginacao){
        return converteDados(categoriaRepository.findAllVideosByCategoriaId(id, paginacao));
    }

    private List<VideoDTO> converteDados(List<Video> video) {
        return video.stream()
                .map(v -> new VideoDTO(v.getId(), v.getTitulo(),
                        v.getDescricao(), v.getUrl(), v.getAberto(), v.getCategoria()))
                .collect(Collectors.toList());
    }
}
