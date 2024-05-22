package alura.backend.challenge.edicao1.domain.repository;

import alura.backend.challenge.edicao1.domain.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {
    Page<Video> findAllByAbertoTrue(Pageable paginacao);

    Page<Video> findAllByTituloContaining(String titulo, Pageable paginacao);

}
