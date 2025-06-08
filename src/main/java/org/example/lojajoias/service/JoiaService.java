package org.example.lojajoias.service;

import org.example.lojajoias.domain.Joia;
import org.example.lojajoias.repository.JoiaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JoiaService {

    JoiaRepository repository;

    public JoiaService(JoiaRepository repository) {
        this.repository = repository;
    }

    public  void create(Joia j){
        repository.save(j);
    }

    public void updated(){}

    public List<Joia> getAll(){
        return repository.findByIsDeleteIsNull();
    }

    public Joia getById(Long id){
        return repository.findById(id).get();
    }

    public void softDelete(Long id) {
        Joia joia = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Joia não encontrada"));
        joia.setIsDelete(LocalDateTime.now());
        repository.save(joia);
    }
}
