package br.com.fortfinance.controller;

import br.com.fortfinance.DAO.Idespesas;
import br.com.fortfinance.model.Despesas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/despesas")
public class DespesasController {

    @Autowired
    private Idespesas dao;

    @PostMapping
    public ResponseEntity<Despesas> salvar(@RequestBody Despesas despesas) {
        try {
            Despesas despesaSalva = dao.save(despesas);
            return new ResponseEntity<>(despesaSalva, HttpStatus.CREATED); // Status 201
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Status 500
        }
    }

    @GetMapping
    public ResponseEntity<List<Despesas>> listar() {
        try {
            List<Despesas> despesas = dao.findAll();
            if (despesas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Status 204
            }
            return new ResponseEntity<>(despesas, HttpStatus.OK); // Status 200
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Status 500
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Despesas> buscarPorId(@PathVariable("id") Integer id) {
        Optional<Despesas> despesa = dao.findById(id);
        return despesa.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Status 404 se não encontrado
    }

    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<Despesas> deletar(@PathVariable("id") Integer id) {
        dao.deleteById(id);
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/total/{email}")
    public ResponseEntity<Double> totalDespesasPorUsuario(@PathVariable String email) {
        Double total = dao.calcularTotalDespesasPorUsuario(email);
        return ResponseEntity.ok(total != null ? total : 0.0);
    }
}
