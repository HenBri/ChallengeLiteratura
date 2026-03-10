package pe.challenge.literatura.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.challenge.literatura.dtos.DatosBook;
import pe.challenge.literatura.dtos.DatosRespuesta;
import pe.challenge.literatura.model.Autor;
import pe.challenge.literatura.model.Libro;
import pe.challenge.literatura.repositories.AutorRepository;
import pe.challenge.literatura.repositories.BookRepository;


@Service
@RequiredArgsConstructor
public class BookService {

    private final ConsumoApiService consumoApi;
    private final BookRepository bookRepository;
    private final AutorRepository autorRepository;

    private final ObjectMapper mapper = new ObjectMapper();


    @Transactional
    public void buscarLibroPorTitulo(String titulo) {

        try {
            String json = consumoApi.obtenerDatos(titulo);

            DatosRespuesta respuesta = mapper.readValue(json, DatosRespuesta.class);

            if (respuesta.resultados() == null || respuesta.resultados().isEmpty()) {
                System.out.println("La API respondió, pero la lista de resultados está vacía para: " + titulo);
                return;
            }
            DatosBook datos = respuesta.resultados().getFirst();


            if (bookRepository.existsByTitulo(datos.titulo())) {
                System.out.println("No se puede añadir el mismo libro");
                return;
            }


            System.out.printf("""
                            
                            📖 ----- LIBRO ENCONTRADO -----
                            Título    : %s
                            Autor     : %s
                            Idioma    : %s
                            Descargas : %.0f
                            -------------------------------
                            """, datos.titulo(), datos.autores().getFirst().nombre(),
                    datos.idiomas().getFirst(), datos.descargas());
            Autor autor = new Autor();
            autor.setNombre(datos.autores().getFirst().nombre());
            autor.setNacimiento(datos.autores().getFirst().nacimiento());
            autor.setFallecimiento(datos.autores().getFirst().fallecimiento());

            autorRepository.save(autor);

            Libro libro = new Libro();
            libro.setTitulo(datos.titulo());
            libro.setIdioma(datos.idiomas().getFirst());
            libro.setDescargas(datos.descargas());
            libro.setAutor(autor);


            bookRepository.save(libro);

            System.out.println("Libro guardado correctamente.");

        } catch (Exception e) {
            System.out.println("Error al buscar libro.");
        }
    }

    @Transactional(readOnly = true)
    public void listarLibros() {
        bookRepository.findAll()
                .forEach(l -> System.out.println(l.getTitulo()));
    }

    @Transactional(readOnly = true)
    public void listarAutores() {
        autorRepository.findAll()
                .forEach(a -> System.out.println(a.getNombre()));
    }

    @Transactional(readOnly = true)
    public void listarPorIdioma(String idioma) {
        var libros = bookRepository.findByIdioma(idioma);

        if (libros.isEmpty()) {
            System.out.println("\nNo tenemos libros registrados en el idioma: '" + idioma + "'");
        } else {
            System.out.println("\n--- Libros en idioma '" + idioma + "' ---");
            libros.forEach(l -> System.out.println("- " + l.getTitulo() + " (Autor: " + l.getAutor().getNombre() + ")"));
            System.out.println("----------------------------------\n");
        }
    }

    @Transactional(readOnly = true)
    public void autoresVivos(Integer year) {

        var autores = autorRepository.autoresVivos(year);

        if (autores.isEmpty()) {
            System.out.printf("No hay autores vivos en el año: %d\n", year);
        } else {
            System.out.println("\n--- Autores vivos en el año " + year + " ---");
            autores.forEach(a -> {
                String fallecimiento = (a.getFallecimiento() != null) ? a.getFallecimiento().toString() : "Actualidad";
                System.out.println("- " + a.getNombre() + " (" + a.getNacimiento() + " - " + fallecimiento + ")");
            });
            System.out.println("--------------------------------------\n");
        }
    }
}