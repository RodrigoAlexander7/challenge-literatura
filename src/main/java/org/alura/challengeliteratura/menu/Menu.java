package org.alura.challengeliteratura.menu;

import org.alura.challengeliteratura.model.Autor;
import org.alura.challengeliteratura.model.Libro;
import org.alura.challengeliteratura.service.LiteraturaService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Menu {

    private final LiteraturaService literaturaService;
    private final Scanner scanner = new Scanner(System.in);

    public Menu(LiteraturaService literaturaService) {
        this.literaturaService = literaturaService;
    }

    public void mostrar() {
        int opcion = -1;
        while (opcion != 0) {
            imprimirMenu();
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                opcion = -1;
            }
            procesarOpcion(opcion);
        }
        System.out.println("¡Hasta luego!");
    }

    private void imprimirMenu() {
        System.out.println("\n========== CHALLENGE LITERATURA ==========");
        System.out.println("1 - Buscar libro por título (Gutendex)");
        System.out.println("2 - Listar libros registrados");
        System.out.println("3 - Listar autores registrados");
        System.out.println("4 - Listar autores vivos en un año");
        System.out.println("5 - Estadísticas de libros por idioma");
        System.out.println("0 - Salir");
        System.out.print("Opción: ");
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> buscarLibro();
            case 2 -> listarLibros();
            case 3 -> listarAutores();
            case 4 -> listarAutoresVivos();
            case 5 -> estadisticasPorIdioma();
            case 0 -> {} // salir
            default -> System.out.println("[WARN] Opción inválida, intenta de nuevo.");
        }
    }

    private void buscarLibro() {
        System.out.print("Ingresa el título a buscar: ");
        String titulo = scanner.nextLine().trim();
        if (titulo.isEmpty()) {
            System.out.println("[WARN] El título no puede estar vacío.");
            return;
        }
        literaturaService.buscarYRegistrarLibro(titulo);
    }

    private void listarLibros() {
        List<Libro> libros = literaturaService.listarLibros();
        if (libros.isEmpty()) {
            System.out.println("[INFO] No hay libros registrados.");
            return;
        }
        System.out.println("\n--- Libros registrados (" + libros.size() + ") ---");
        libros.forEach(System.out::println);
    }

    private void listarAutores() {
        List<Autor> autores = literaturaService.listarAutores();
        if (autores.isEmpty()) {
            System.out.println("[INFO] No hay autores registrados.");
            return;
        }
        System.out.println("\n--- Autores registrados (" + autores.size() + ") ---");
        autores.forEach(a -> System.out.println(a + "\n"));
    }

    private void listarAutoresVivos() {
        System.out.print("Ingresa el año: ");
        try {
            int anio = Integer.parseInt(scanner.nextLine().trim());
            List<Autor> autores = literaturaService.listarAutoresVivosEnAnio(anio);
            if (autores.isEmpty()) {
                System.out.println("[INFO] No se encontraron autores vivos en el año " + anio + ".");
                return;
            }
            System.out.println("\n--- Autores vivos en " + anio + " (" + autores.size() + ") ---");
            autores.forEach(a -> System.out.println(a + "\n"));
        } catch (NumberFormatException e) {
            System.out.println("[WARN] Año inválido.");
        }
    }

    private void estadisticasPorIdioma() {
        System.out.println("\n--- Selecciona un idioma ---");
        System.out.println("  es - Español");
        System.out.println("  en - Inglés");
        System.out.println("  fr - Francés");
        System.out.println("  pt - Portugués");
        System.out.print("Idioma: ");
        String idioma = scanner.nextLine().trim().toLowerCase();

        List<String> idiomasValidos = List.of("es", "en", "fr", "pt");
        if (!idiomasValidos.contains(idioma)) {
            System.out.println("[WARN] Idioma no reconocido. Opciones válidas: es, en, fr, pt");
            return;
        }

        literaturaService.estadisticasPorIdioma(idioma);
    }
}

