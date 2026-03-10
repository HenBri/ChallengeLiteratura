package pe.challenge.literatura.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pe.challenge.literatura.model.Autor;

import java.util.List;

public interface AutorRepository extends CrudRepository<Autor, Long> {

    @Query("""
            SELECT a FROM autor a
            WHERE a.nacimiento <= :year
            AND (a.fallecimiento IS NULL OR a.fallecimiento >= :year)
            """)
    List<Autor> autoresVivos(Integer year);
}
