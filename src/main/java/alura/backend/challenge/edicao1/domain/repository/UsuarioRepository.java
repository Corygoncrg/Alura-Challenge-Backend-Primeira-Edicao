package alura.backend.challenge.edicao1.domain.repository;

import alura.backend.challenge.edicao1.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByLogin(String subject);
}
