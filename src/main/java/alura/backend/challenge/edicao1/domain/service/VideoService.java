package alura.backend.challenge.edicao1.domain.service;

import alura.backend.challenge.edicao1.domain.dto.video.VideoDTO;
import alura.backend.challenge.edicao1.domain.model.Video;
import alura.backend.challenge.edicao1.domain.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VideoService {

    @Autowired
    private VideoRepository repository;

    public VideoDTO obterPorId(Long id) {
        Optional<Video> categoria = repository.findById(id);
        if (categoria.isPresent()) {
            Video v = categoria.get();
            return new VideoDTO(v.getId(), v.getTitulo(),
                    v.getDescricao(), v.getUrl(), v.getAberto(), v.getCategoria());
        }
        return null;
    }
}
