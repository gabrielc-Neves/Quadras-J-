package com.example.QuadrasJA.services;

import com.example.QuadrasJA.models.Quadra;
import com.example.QuadrasJA.repositories.QuadraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuadraService {

    @Autowired
    private QuadraRepository quadraRepository;

    public Quadra cadastrarQuadra(Quadra quadra) {
        return quadraRepository.save(quadra);
    }

    public List<Quadra> listarQuadras() {
        return quadraRepository.findAll();
    }
    public Quadra atualizarQuadra(Long id, Quadra quadraAtualizada) {
        return quadraRepository.findById(id).map(quadra -> {
            quadra.setNome(quadraAtualizada.getNome());
            quadra.setDescricao(quadraAtualizada.getDescricao());
            quadra.setCapacidade(quadraAtualizada.getCapacidade());
            return quadraRepository.save(quadra);
        }).orElseThrow(() -> new RuntimeException("Quadra não encontrada!"));
    }
    
    public void deletarQuadra(Long id) {
        if (!quadraRepository.existsById(id)) {
            throw new RuntimeException("Quadra não encontrada!");
        }
        quadraRepository.deleteById(id);
    }
}
