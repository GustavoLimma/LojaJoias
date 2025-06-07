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
        model.addAttribute("joiaList", service.getAll());
        return "index";
    }

    @PostMapping("/doProcessSaveWithFile")
    public String doProcessSaveWithFile(@ModelAttribute @Valid Joia j, Errors errors, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
        if (errors.hasErrors()) {
            return "cadastro";
        } else {
            j.setImagemUri(file.getOriginalFilename());
            service.create(j);
            fileStorageService.save(file);
            redirectAttributes.addAttribute("msg", "Cadastro realizado com sucesso");
            return "redire" +
                    "ct:/";
        }
    }

    @PostMapping("/doProcessSave")
    public String doProcessSave(@ModelAttribute @Valid Joia joia, Errors errors){
        if (errors.hasErrors()) {
            return "cadastro";
        } else {
            service.create(joia);
            return "redirect:/";
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
        service.delete(id);
        return "redirect:/";
    }

    @GetMapping("/editar/{id}")
    public String getEdit(Model model, @PathVariable Long id){
        model.addAttribute("joia", service.getById(id));
        return "edite";
    }
}
