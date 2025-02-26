package com.example.QuadrasJA.controllers;

import com.example.QuadrasJA.models.Reserva;
import com.example.QuadrasJA.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping("/criar")
    public ResponseEntity<?> criarReserva(@RequestBody Reserva reserva) {
        try {
            Reserva novaReserva = reservaService.criarReserva(reserva);
            return ResponseEntity.ok(novaReserva);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Reserva>> listarReservas(@RequestParam String data) {
        LocalDate dataReserva = LocalDate.parse(data);
        return ResponseEntity.ok(reservaService.listarReservasPorData(dataReserva));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Reserva> atualizarReserva(@PathVariable Long id, @RequestBody Reserva reserva) {
        return ResponseEntity.ok(reservaService.atualizarReserva(id, reserva));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarReserva(@PathVariable Long id) {
        reservaService.deletarReserva(id);
        return ResponseEntity.ok("Reserva deletada com sucesso.");
    }
}
