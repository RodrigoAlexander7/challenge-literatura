package org.alura.challengeliteratura.model;
import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String titulo;

    private String idioma;
    private Integer descargas;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libro() {}

    public Libro(String titulo, String idioma, Integer descargas, Autor autor) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.descargas = descargas;
        this.autor = autor;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
    public Integer getDescargas() { return descargas; }
    public void setDescargas(Integer descargas) { this.descargas = descargas; }
    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }

    @Override
    public String toString() {
        return String.format(
                "┌─────────────────────────────┐%n" +
                "  Título   : %s%n" +
                "  Autor    : %s%n" +
                "  Idioma   : %s%n" +
                "  Descargas: %d%n" +
                "└─────────────────────────────┘",
                titulo,
                autor != null ? autor.getNombre() : "desconocido",
                idioma,
                descargas != null ? descargas : 0);
    }
}

