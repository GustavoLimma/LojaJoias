package org.example.lojajoias.controler;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.lojajoias.domain.Joia;
import org.example.lojajoias.service.JoiaService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class JoiaController {

    JoiaService service;

    public JoiaController(JoiaService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String getHomePage(Model model, HttpServletRequest request){

        HttpSession session = request.getSession();
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
    public String doProcessSave(@ModelAttribute @Valid Joia joia, Errors errors, RedirectAttributes redirectAttributes, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("joia", joia);
            return (joia.getId() != null) ? "edite" : "cadastro";
        } else {
            if (joia.getId() == null) {
                service.create(joia);
                redirectAttributes.addFlashAttribute("mensasgem", "Joia editada com sucesso!");
            } else {
                service.updated(joia);
                redirectAttributes.addFlashAttribute("mensasgem", "Joia cadastrada com sucesso!");
            }
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
    public String doProcessDelete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        service.softDelete(id);
        redirectAttributes.addFlashAttribute("mensasgem", "Joia deletada com sucesso!");
        return "redirect:/admin";
    }

    @GetMapping("/restaurar/{id}")
    public String doProcessRestaur(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        service.restore(id);
        redirectAttributes.addFlashAttribute("mensasgem", "Joia restaurada com sucesso!");
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
    public String finalizarCompra(HttpSession session, RedirectAttributes redirectAttributes) {

        session.invalidate();
        redirectAttributes.addFlashAttribute("mensagem", "Compra finalizada com sucesso!");
        return "redirect:/";
    }

}
