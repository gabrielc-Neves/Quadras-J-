package com.example.QuadrasJA.services;

import com.example.QuadrasJA.models.Reserva;
import com.example.QuadrasJA.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    public Reserva criarReserva(Reserva reserva) {
        // Verifica se a quadra já está reservada nesse horário
        List<Reserva> reservasNoMesmoDia = reservaRepository.findByDataReserva(reserva.getDataReserva());

        boolean conflito = reservasNoMesmoDia.stream().anyMatch(r -> 
            r.getQuadra().getId().equals(reserva.getQuadra().getId()) &&
            (r.getHoraInicio().isBefore(reserva.getHoraFim()) && r.getHoraFim().isAfter(reserva.getHoraInicio()))
        );

        if (conflito) {
            throw new RuntimeException("Quadra já reservada para este horário!");
        }

        return reservaRepository.save(reserva);
    }

    public List<Reserva> listarReservasPorData(LocalDate data) {
        return reservaRepository.findByDataReserva(data);
    }

    public Reserva atualizarReserva(Long id, Reserva reservaAtualizada) {
        return reservaRepository.findById(id).map(reserva -> {
            reserva.setUsuario(reservaAtualizada.getUsuario());
            reserva.setQuadra(reservaAtualizada.getQuadra());
            reserva.setDataReserva(reservaAtualizada.getDataReserva());
            reserva.setHoraInicio(reservaAtualizada.getHoraInicio());
            reserva.setHoraFim(reservaAtualizada.getHoraFim());
            return reservaRepository.save(reserva);
        }).orElseThrow(() -> new RuntimeException("Reserva não encontrada!"));
    }
    
    public void deletarReserva(Long id) {
        if (!reservaRepository.existsById(id)) {
            throw new RuntimeException("Reserva não encontrada!");
        }
        reservaRepository.deleteById(id);
    }
}