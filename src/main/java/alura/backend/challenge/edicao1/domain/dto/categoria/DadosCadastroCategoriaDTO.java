package alura.backend.challenge.edicao1.domain.dto.categoria;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroCategoriaDTO(
        @NotNull String titulo,
        @NotNull @Pattern(regexp = "^#[a-zA-Z0-9]{6}$")
        String cor
) {
}
