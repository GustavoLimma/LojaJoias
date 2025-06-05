package org.example.lojajoias.repository;

import org.example.lojajoias.domain.Joia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface JoiaRepository extends JpaRepository<Joia, Long> {

}
