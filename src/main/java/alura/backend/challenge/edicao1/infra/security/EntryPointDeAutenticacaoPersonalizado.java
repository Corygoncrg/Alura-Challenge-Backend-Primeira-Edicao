package alura.backend.challenge.edicao1.infra.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class EntryPointDeAutenticacaoPersonalizado implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if ("/login".equals(request.getRequestURI())) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            PrintWriter writer = response.getWriter();
            writer.write("{\"erro\": \"Credenciais inválidas: usuário ou senha incorretos.\"}");
            writer.flush();
            return;
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        PrintWriter writer = response.getWriter();
        writer.write("{\"erro\": \"Não autorizado\"}");
        writer.flush();
    }
}
