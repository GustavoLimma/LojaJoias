package org.example.lojajoias.controler;

import jakarta.validation.Valid;
import org.example.lojajoias.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.lojajoias.domain.Joia;
import org.example.lojajoias.service.JoiaService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class JoiaController {

    JoiaService service;
    FileStorageService fileStorageService;

    public JoiaController(JoiaService service, FileStorageService fileStorageService) {
        this.service = service;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/")
    public String getHomePage(Model model, HttpServletRequest request){

        HttpSession session = request.getSession();
        session.setAttribute("msg", "Hello World");
        model.addAttribute("msg", session.getAttribute("msg"));
        model.addAttribute("joiaList", service.getAllNoDelete());

        List<Joia> carrinho = (List<Joia>) session.getAttribute("carrinho");
        int quantidadeCarrinho = (carrinho != null) ? carrinho.size() : 0;
        model.addAttribute("quantidadeCarrinho", quantidadeCarrinho);

        return "index";
    }

    @GetMapping("/admin")
    public String adminPage(Model model, HttpServletRequest request){

        HttpSession session = request.getSession();
        session.setAttribute("msg", "Hello World");
        model.addAttribute("msg", session.getAttribute("msg"));
        model.addAttribute("joiaList", service.getAll());
        return "admin";
    }

    @PostMapping("/doProcessSave")
    public String doProcessSave(@ModelAttribute @Valid Joia joia, Errors errors){
        if (errors.hasErrors()) {
            return "cadastro";
        } else {
            service.create(joia);
            return "redirect:/admin";
        }
    }

    @GetMapping("cadastro")
    public String getCadastroPage(Model model){

        Joia joia = new Joia();
        model.addAttribute("joia", joia);

        return "cadastro";
    }

    @GetMapping("/deletar/{id}")
    public String doProcessDelete(@PathVariable Long id){
        service.softDelete(id);
        return "redirect:/admin";
    }

    @GetMapping("/restaurar/{id}")
    public String doProcessRestaur(@PathVariable Long id){
        service.restore(id);
        return "redirect:/admin";
    }

    @GetMapping("/editar/{id}")
    public String getEdit(Model model, @PathVariable Long id){
        model.addAttribute("joia", service.getById(id));
        return "edite";
    }

    @GetMapping("/verCarrinho")
    public String verCarrinho(HttpSession session,Model model, RedirectAttributes redirectAttributes){
        List<Joia> carrinho = (List<Joia>) session.getAttribute("carrinho");

        if (carrinho == null || carrinho.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensagem", "Carrinho está vazio.");
            return "redirect:/";
        }

        model.addAttribute("carrinho", carrinho);
        return "Carrinho";
    }

    @GetMapping("/adicionarCarrinho")
    public String adicionarCarrinho(@RequestParam Long id, HttpSession session) {
        Joia joia = service.getById(id);

        List<Joia> carrinho = (List<Joia>) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new ArrayList<>();
            session.setAttribute("carrinho", carrinho);
        }

        carrinho.add(joia);

        return "redirect:/";
    }

    @GetMapping("/finalizarCompra")
    public String finalizarCompra(HttpSession session) {

        session.invalidate();

        return "redirect:/";
    }

}
