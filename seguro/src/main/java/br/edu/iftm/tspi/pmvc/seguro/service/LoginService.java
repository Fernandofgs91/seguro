package br.edu.iftm.tspi.pmvc.seguro.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import br.edu.iftm.tspi.pmvc.seguro.domain.Login;
import br.edu.iftm.tspi.pmvc.seguro.repository.LoginRepository;
import java.util.Optional;

@Service
public class LoginService {

    private final LoginRepository repository;
    private final BCryptPasswordEncoder encoder;

    public LoginService(LoginRepository repository, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public boolean usuarioExiste(String usuario) {
        return repository.findByUsuario(usuario).isPresent();
    }

    public void salvar(Login login) {
        if (login == null || !StringUtils.hasText(login.getUsuario()) || !StringUtils.hasText(login.getSenha())) {
            throw new IllegalArgumentException("Usuário e senha são obrigatórios para cadastro.");
        }

        if (usuarioExiste(login.getUsuario())) {
            throw new IllegalStateException("Usuário já cadastrado! Escolha outro nome.");
        }

        login.setSenha(encoder.encode(login.getSenha()));
        repository.save(login);
    }

    public boolean verificaLogin(Login loginDigitado) {
        if (loginDigitado == null || !StringUtils.hasText(loginDigitado.getUsuario()) || !StringUtils.hasText(loginDigitado.getSenha())) {
            throw new IllegalArgumentException("Usuário e senha não podem ser nulos ou vazios.");
        }

        Optional<Login> loginBanco = Optional.ofNullable(repository.findByUsuario(loginDigitado.getUsuario()));
        return loginBanco.isPresent() && encoder.matches(loginDigitado.getSenha(), loginBanco.get().getSenha());
    }
}
