
package com.info.email.security;
import com.info.email.model.Usuario;
import com.info.email.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String login) 
            throws UsernameNotFoundException {
        
    Usuario usuario = repository.findByLogin(login).orElse(null);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return new User(
            usuario.getLogin(),
            usuario.getSenha(),
            List.of(new SimpleGrantedAuthority(
                usuario.getRole()))
        );
    }
}
