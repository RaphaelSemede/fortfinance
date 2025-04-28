package br.com.fortfinance.DAO;

import br.com.fortfinance.model.Receitas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface Ireceitas extends JpaRepository<Receitas, Integer> {
    Page<Receitas> findAll(Pageable pageable);
    Optional<Receitas> findById(Integer id);

    @Query("SELECT SUM(d.valor) FROM Receitas d WHERE d.usuario.email = :email")
    Double calcularTotalReceitasPorUsuario(@Param("email") String email);
}
