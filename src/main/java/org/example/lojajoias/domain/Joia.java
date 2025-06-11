package org.example.lojajoias.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Enabled;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Joia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Digite o nome da joia que deseja cadastrar")
    String nome;

    @Min(value = 1, message = "O valor da joia deve ser maior que 1")
    Double valor;

    @NotBlank(message = "Digite as pedras da joia(se nenhuma, digite nulo)")
    String pedras;

    @Min(value = 0, message = "A quantidades de joias deve ser maior que 0")
    int quantidade;

    String imagemUri;

    LocalDateTime isDelete;
}
