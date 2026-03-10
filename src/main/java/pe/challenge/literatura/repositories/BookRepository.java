package pe.challenge.literatura.repositories;

import org.springframework.data.repository.CrudRepository;
import pe.challenge.literatura.model.Libro;

import java.util.List;

public interface BookRepository extends CrudRepository<Libro, Long> {

    List<Libro> findByIdioma(String idioma);

    boolean existsByTitulo(String titulo);

}
