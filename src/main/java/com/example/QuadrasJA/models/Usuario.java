package com.example.QuadrasJA.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    private String senha;
    
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo; // CLIENTE ou ADMINISTRADOR
    //private String tipo_Usuario;
}
