package br.com.fortfinance.DAO;

import br.com.fortfinance.model.Despesas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface Idespesas extends JpaRepository<Despesas, Integer> {
    Page<Despesas> findAll(Pageable pageable);
    Optional<Despesas> findById(Integer id);

    @Query("SELECT SUM(d.valor) FROM Despesas d WHERE d.usuario.email = :email")
    Double calcularTotalDespesasPorUsuario(@Param("email") String email);
}
