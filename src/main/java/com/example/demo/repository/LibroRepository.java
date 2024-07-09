package com.example.demo.repository;
import java.util.List;
import com.example.demo.entity.LibroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LibroRepository   extends JpaRepository<LibroEntity, Long> {
    @Query("SELECT l FROM LibroEntity l WHERE l.lenguaje >= :idioma")
    List<LibroEntity> findForLanguaje(String idioma);
}
