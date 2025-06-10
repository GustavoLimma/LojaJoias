package org.example.lojajoias.service;

import org.example.lojajoias.domain.Joia;
import org.example.lojajoias.repository.JoiaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class JoiaService {

    JoiaRepository repository;

    public JoiaService(JoiaRepository repository) {
        this.repository = repository;
    }

    public  void create(Joia j){
        j.setImagemUri(getRandomImageUrl());
        repository.save(j);
    }

    public void updated(Joia j){
        repository.save(j);
    }

    public List<Joia> getAllNoDelete(){
        return repository.findByIsDeleteIsNull();
    }

    public Joia getById(Long id){
        return repository.findById(id).get();
    }

    public List<Joia> getAll(){
        return repository.findAll();
    }

    public void softDelete(Long id) {
        Joia joia = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Joia não encontrada"));
        joia.setIsDelete(LocalDateTime.now());
        repository.save(joia);
    }

    public void restore(Long id) {
        Joia joia = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Joia não encontrada"));
        joia.setIsDelete(null);
        repository.save(joia);
    }

    private String getRandomImageUrl() {
        String[] images = {
                "/imagens/joia1.webp",
                "/imagens/Joia2.webp",
                "/imagens/Joia3.jpg",
        };
        Random random = new Random();
        return images[random.nextInt(images.length)];
    }
}
