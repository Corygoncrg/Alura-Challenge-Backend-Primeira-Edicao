package alura.backend.challenge.edicao1.domain.repository;

import alura.backend.challenge.edicao1.domain.model.Categoria;
import alura.backend.challenge.edicao1.domain.model.Video;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @Query("SELECT v FROM Categoria c JOIN c.videos v WHERE c.id = :id")
    List<Video> findAllVideosByCategoriaId(@Param("id") Long id, Pageable pageable);


}
