package com.info.email.controller;

import com.info.email.model.Usuario;
import com.info.email.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/redirecionar")
    public String redirecionar(Authentication auth) {
        Usuario usuario = repository.findByLogin(auth.getName()).orElse(null);
        if (usuario != null && usuario.getTipo() == 1) {
            return "redirect:/usuarios";
        }
        return "redirect:/mensagens";
    }

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", repository.findAll());
        return "usuarios"; 
    }
    
    @GetMapping("/novousuario")
    public String novo() {
        return "novousuario"; 
    }

    @PostMapping("/novousuario")
    public String salvarUsuario(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        if (usuario.getTipo() == 0) {
            usuario.setTipo(2); 
        }
        repository.save(usuario);
        return "redirect:/usuarios";
    }
}
