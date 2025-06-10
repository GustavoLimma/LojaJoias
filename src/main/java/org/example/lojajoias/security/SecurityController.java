package org.example.lojajoias.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.lojajoias.domain.Usuario;
import org.example.lojajoias.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class SecurityController {

    UsuarioRepository usuarioRepository;
    PasswordEncoder passwordEncoder;

    public SecurityController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/cadastroUsuario")
    public String mostrarFormularioCadastro() {
        return "register";
    }

    @PostMapping("/salvarUsuario")
    public String salvarUsuario(@RequestParam String username,
                                @RequestParam String password,
                                @RequestParam(required = false) boolean isAdmin) {
        Usuario novo = new Usuario();
        novo.setUsername(username);
        novo.setPassword(passwordEncoder.encode(password));
        novo.setIsAdmin(isAdmin);
        usuarioRepository.save(novo);
        return "redirect:/login?cadastroSucesso";
    }


    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @GetMapping("/redirecionar")
    public String redirecionar(HttpServletRequest request) {

        if (request.isUserInRole("ADMIN")) {
            return "redirect:/admin";
        } else {
            return "redirect:/";
        }
    }

}