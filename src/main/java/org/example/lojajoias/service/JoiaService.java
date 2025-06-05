package org.example.lojajoias.service;

import org.example.lojajoias.domain.Joia;
import org.example.lojajoias.repository.JoiaRepository;
import org.springframework.stereotype.Service;

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
    public void delete(Long id){
        repository.deleteById(id);
    }
    public void updated(){}
    public List<Joia> getAll(){
        return repository.findAll();
    }
    public Joia getById(Long id){
        return repository.findById(id).get();
    }
}
