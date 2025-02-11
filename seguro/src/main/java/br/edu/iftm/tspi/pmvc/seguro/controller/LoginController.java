package br.edu.iftm.tspi.pmvc.seguro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import br.edu.iftm.tspi.pmvc.seguro.domain.Login;
import br.edu.iftm.tspi.pmvc.seguro.service.LoginService;

@Controller
public class LoginController {

    private final LoginService service;

    public LoginController(LoginService service) {
        this.service = service;
    }

    @PostMapping("/login/entrar")
    public String validarLogin(Login login, Model model) {
        if (login == null || login.getUsuario() == null || login.getUsuario().trim().isEmpty() ||
            login.getSenha() == null || login.getSenha().trim().isEmpty()) {
            model.addAttribute("mensagem", "Usuário e senha são obrigatórios!");
            return "login/login";
        }

        if (service.verificaLogin(login)) {
            return "redirect:/home"; 
        } else {
            model.addAttribute("mensagem", "Usuário ou senha inválidos.");
            return "login/login";
        }
    }

    @GetMapping("/")
    public String telaInicial() {
        return "login/login";
    }

    @GetMapping("/login/telaNovoUsuario")
    public String novo() {
        return "login/cadastro";
    }

    @PostMapping("/login/novoUsuario")
    public String novoUsuario(Login loginDigitado, Model model) {
        if (loginDigitado == null || loginDigitado.getUsuario() == null || loginDigitado.getUsuario().trim().isEmpty() ||
            loginDigitado.getSenha() == null || loginDigitado.getSenha().trim().isEmpty()) {
            model.addAttribute("mensagem", "Usuário e senha são obrigatórios para cadastro!");
            return "login/cadastro";
        }

        if (service.usuarioExiste(loginDigitado.getUsuario())) {
            model.addAttribute("mensagem", "Usuário já cadastrado! Escolha outro nome.");
            return "login/cadastro"; 
        }

        service.salvar(loginDigitado);
        model.addAttribute("mensagem", "Usuário cadastrado com sucesso!");
        return "login/login";
    }
}
