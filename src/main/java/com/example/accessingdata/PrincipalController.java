package com.example.accessingdata;
//wendel
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller // Isso significa que esta classe é um Controlador
@RequestMapping(path = "/demo") // Isso significa que os URLs começam com / Demo (após o caminho do aplicativo)
@CrossOrigin(origins = "*")
public class PrincipalController {
    @Autowired // faz o star do nosso objeto
    private UsuarioRepository uRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNovoUsuario(@RequestParam String nome, @RequestParam String email) {

        if(uRepository.findByEmail(email) != null ){
            return "O email digitado já existe.";
        }

        try {
           Usuario u = new Usuario();
           u.setNome(nome);
            u.setEmail(email);
            uRepository.save(u);
            return "Ok ao gravar.";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Usuario> getAllUsuarios() {
        return uRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        // return uRepository.findAll();
    }
    // PUT atualizar
    @PutMapping(path = "/update/{id}")
    public @ResponseBody String updateUsuario(@PathVariable int id, @RequestParam String nome,
            @RequestParam String email) {
        Usuario u = uRepository.findById(id);
        u.setNome(nome);
        u.setEmail(email);
        uRepository.save(u);
        return "Ok ao atualizar.";
    }
    // DELETE apagar
    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody String deleteUsuario(@PathVariable int id) {
        uRepository.deleteById(id);
        return "Ok ao apagar.";
    }
}
