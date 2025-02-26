package com.example.QuadrasJA.services;

import com.example.QuadrasJA.models.Usuario;
import com.example.QuadrasJA.repositories.UsuarioRepository;
import com.example.QuadrasJA.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

//Essa classe processa o login e gera um token JWT válido.
@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String autenticar(String email, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (passwordEncoder.matches(senha, usuario.getSenha())) { 
                return jwtUtil.generateToken(email);
            }
        }
        throw new RuntimeException("Usuário ou senha inválidos.");
    }
}
