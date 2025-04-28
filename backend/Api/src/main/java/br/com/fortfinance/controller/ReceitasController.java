package br.com.fortfinance.controller;

import br.com.fortfinance.DAO.Ireceitas;
import br.com.fortfinance.model.Receitas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/receitas")
public class ReceitasController {

    @Autowired
    private Ireceitas dao;

    @PostMapping
    public ResponseEntity<Receitas> salvar(@RequestBody Receitas receitas) {
        try {
            Receitas receitaSalva = dao.save(receitas);
            return new ResponseEntity<>(receitaSalva, HttpStatus.CREATED); // Status 201
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Status 500
        }
    }

    @GetMapping
    public ResponseEntity<List<Receitas>> listar() {
        try {
            List<Receitas> receitas = dao.findAll();
            if (receitas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Status 204
            }
            return new ResponseEntity<>(receitas, HttpStatus.OK); // Status 200
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Status 500
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receitas> buscarPorId(@PathVariable("id") Integer id) {
        Optional<Receitas> receitas = dao.findById(id);
        return receitas.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Status 404 se não encontrado
    }

    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<Receitas> deletar(@PathVariable("id") Integer id) {
        dao.deleteById(id);
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/total/{email}")
    public ResponseEntity<Double> totalReceitasPorUsuario(@PathVariable String email) {
        Double total = dao.calcularTotalReceitasPorUsuario(email);
        return ResponseEntity.ok(total != null ? total : 0.0);
    }
}
