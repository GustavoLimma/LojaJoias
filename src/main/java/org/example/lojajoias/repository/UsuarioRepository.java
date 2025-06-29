package org.example.lojajoias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.lojajoias.domain.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findUsuarioByUsername(String username);
}