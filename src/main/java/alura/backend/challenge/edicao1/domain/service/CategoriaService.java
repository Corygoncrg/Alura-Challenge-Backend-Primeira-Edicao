package alura.backend.challenge.edicao1.domain.service;

import alura.backend.challenge.edicao1.domain.dto.categoria.CategoriaDTO;
import alura.backend.challenge.edicao1.domain.model.Categoria;
import alura.backend.challenge.edicao1.domain.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

public CategoriaDTO obterPorId(Long id) {
    Optional<Categoria> categoria = repository.findById(id);
    if (categoria.isPresent()) {
        Categoria c = categoria.get();
        return new CategoriaDTO(c.getId(), c.getTitulo(),
                c.getCor());
    }
    return null;
    }

}
