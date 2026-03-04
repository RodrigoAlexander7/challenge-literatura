package org.alura.challengeliteratura.service;

import org.alura.challengeliteratura.client.GutendexClient;
import org.alura.challengeliteratura.dto.AutorDTO;
import org.alura.challengeliteratura.dto.GutendexRespuestaDTO;
import org.alura.challengeliteratura.dto.LibroDTO;
import org.alura.challengeliteratura.model.Autor;
import org.alura.challengeliteratura.model.Libro;
import org.alura.challengeliteratura.repository.AutorRepository;
import org.alura.challengeliteratura.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
public class LiteraturaService {

    private final GutendexClient gutendexClient;
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public LiteraturaService(GutendexClient gutendexClient,
                             LibroRepository libroRepository,
                             AutorRepository autorRepository) {
        this.gutendexClient = gutendexClient;
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void buscarYRegistrarLibro(String titulo) {
        GutendexRespuestaDTO respuesta = gutendexClient.buscarLibroPorTitulo(titulo);

        if (respuesta == null || respuesta.resultados().isEmpty()) {
            System.out.println("[INFO] No se encontró ningún libro con ese título en Gutendex.");
            return;
        }

        LibroDTO libroDTO = respuesta.resultados().get(0);

        if (libroRepository.findByTituloIgnoreCase(libroDTO.titulo()).isPresent()) {
            System.out.println("[INFO] El libro '" + libroDTO.titulo() + "' ya está registrado en la base de datos.");
            return;
        }

        Autor autor = resolverAutor(libroDTO.autores());
        String idioma = libroDTO.idiomas().isEmpty() ? "desconocido" : libroDTO.idiomas().get(0);
        Libro libro = new Libro(libroDTO.titulo(), idioma, libroDTO.descargas(), autor);

        libroRepository.save(libro);
        System.out.println("[OK] Libro registrado exitosamente:");
        System.out.println(libro);
    }

    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }

    public List<Autor> listarAutoresVivosEnAnio(int anio) {
        return autorRepository.findAutoresVivosEnAnio(anio);
    }

    public void estadisticasPorIdioma(String idioma) {
        List<Libro> libros = libroRepository.findByIdioma(idioma);

        long total = libros.size();

        if (total == 0) {
            System.out.println("[INFO] No hay libros registrados en el idioma: " + idioma);
            return;
        }

        OptionalDouble promDescargas = libros.stream()
                .mapToInt(l -> l.getDescargas() != null ? l.getDescargas() : 0)
                .average();

        Libro masDescargado = libros.stream()
                .max(Comparator.comparingInt(l -> l.getDescargas() != null ? l.getDescargas() : 0))
                .orElse(null);

        System.out.println("\n--- Estadísticas para idioma: [" + idioma + "] ---");
        System.out.println("  Total de libros       : " + total);
        System.out.printf( "  Promedio de descargas : %.0f%n", promDescargas.orElse(0));
        if (masDescargado != null) {
            System.out.println("  Libro más descargado  : " + masDescargado.getTitulo()
                    + " (" + masDescargado.getDescargas() + " descargas)");
        }
    }

    // --- Helpers ---

    private Autor resolverAutor(List<AutorDTO> autoresDTO) {
        if (autoresDTO == null || autoresDTO.isEmpty()) {
            return obtenerOCrearAutor("Desconocido", null, null);
        }
        AutorDTO dto = autoresDTO.get(0);
        return obtenerOCrearAutor(dto.nombre(), dto.anioNacimiento(), dto.anioFallecimiento());
    }

    private Autor obtenerOCrearAutor(String nombre, Integer anioNacimiento, Integer anioFallecimiento) {
        Optional<Autor> existente = autorRepository.findByNombreIgnoreCase(nombre);
        return existente.orElseGet(() -> autorRepository.save(new Autor(nombre, anioNacimiento, anioFallecimiento)));
    }
}

