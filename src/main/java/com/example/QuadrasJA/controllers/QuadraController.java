package com.example.QuadrasJA.controllers;

import com.example.QuadrasJA.models.Quadra;
import com.example.QuadrasJA.services.QuadraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quadras")
public class QuadraController {

    @Autowired
    private QuadraService quadraService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Quadra> cadastrarQuadra(@RequestBody Quadra quadra) {
        Quadra novaQuadra = quadraService.cadastrarQuadra(quadra);
        return ResponseEntity.ok(novaQuadra);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Quadra>> listarQuadras() {
        return ResponseEntity.ok(quadraService.listarQuadras());
    }
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Quadra> atualizarQuadra(@PathVariable Long id, @RequestBody Quadra quadra) {
        return ResponseEntity.ok(quadraService.atualizarQuadra(id, quadra));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarQuadra(@PathVariable Long id) {
        quadraService.deletarQuadra(id);
        return ResponseEntity.ok("Quadra deletada com sucesso.");
    }
}
