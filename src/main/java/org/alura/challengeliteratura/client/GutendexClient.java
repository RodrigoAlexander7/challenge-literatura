package org.alura.challengeliteratura.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.alura.challengeliteratura.dto.GutendexRespuestaDTO;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Component
public class GutendexClient {

    private static final String BASE_URL = "https://gutendex.com/books/";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GutendexRespuestaDTO buscarLibroPorTitulo(String titulo) {
        try {
            String query = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "?search=" + query))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), GutendexRespuestaDTO.class);
        } catch (IOException | InterruptedException e) {
            System.out.println("[ERROR] No se pudo conectar con Gutendex: " + e.getMessage());
            Thread.currentThread().interrupt();
            return null;
        }
    }
}

