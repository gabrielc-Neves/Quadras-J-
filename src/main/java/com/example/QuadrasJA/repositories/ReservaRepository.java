package com.example.QuadrasJA.repositories;

import com.example.QuadrasJA.models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByDataReserva(LocalDate data);
}
