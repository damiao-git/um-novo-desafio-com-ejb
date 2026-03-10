package com.example.backend.rest;

import com.example.backend.service.BeneficioService;
import com.example.ejb.entity.Beneficio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/beneficios")
public class BeneficioController {

    private final BeneficioService service;

    public BeneficioController(BeneficioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Beneficio>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Beneficio> buscar(@PathVariable Long id) {
        Beneficio b = service.buscarPorId(id);
        return b != null ? ResponseEntity.ok(b) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Beneficio> criar(@RequestBody Beneficio beneficio) {
        Beneficio criado = service.criar(beneficio);
        return ResponseEntity.ok(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Beneficio> atualizar(@PathVariable Long id, @RequestBody Beneficio beneficio) {
        beneficio.setId(id);
        Beneficio atualizado = service.atualizar(beneficio);
        return atualizado != null ? ResponseEntity.ok(atualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/transferir")
    public ResponseEntity<Map<String, Beneficio>> transferir(
            @RequestParam Long origemId,
            @RequestParam Long destinoId,
            @RequestParam BigDecimal valor) {

        service.transferir(origemId, destinoId, valor);

        Beneficio origem = service.buscarPorId(origemId);
        Beneficio destino = service.buscarPorId(destinoId);

        Map<String, Beneficio> resultado = new HashMap<>();
        resultado.put("origem", origem);
        resultado.put("destino", destino);

        return ResponseEntity.ok(resultado);
    }
}
