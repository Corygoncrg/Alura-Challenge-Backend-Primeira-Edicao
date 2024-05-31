package alura.backend.challenge.edicao1.domain.repository;

import alura.backend.challenge.edicao1.domain.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface VideoRepository extends JpaRepository<Video, Long> {
    Page<Video> findAllByAbertoTrue(Pageable paginacao);

    Page<Video> findAllByTituloContaining(String titulo, Pageable paginacao);

    @Query("select v from Video v join v.categoria c where c.id = :idCategoria")
    List<Video> findByCategoria(@Param("idCategoria") Long idCategoria);

    Page<Video> findTop5ByOrderByIdAsc(Pageable paginacao);
}
