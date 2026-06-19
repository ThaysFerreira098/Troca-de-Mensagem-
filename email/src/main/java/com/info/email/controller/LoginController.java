
package com.info.email.controller;
import com.info.email.model.Usuario;
import com.info.email.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository repository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/redirecionar")
    public String redirecionar(Authentication auth) {

        Usuario usuario = repository.findByLogin(auth.getName()).orElse(null);
        if (usuario.getTipo() == 1) {
            return "redirect:/usuarios";
        }
        return "redirect:/mensagens";
    }
    
    @GetMapping("/usuarios")
    public String listarUsuarios() {
        return "usuarios"; 
    }

    
}
