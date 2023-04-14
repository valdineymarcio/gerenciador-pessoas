package br.com.api.gerenciadorpessoas.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String logradouro;

    private String cep;

    private String numero;

    private String cidade;
    private Boolean enderecoPrincipal;
    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;


    public Boolean isPrincipal() {
        return enderecoPrincipal;
    }

    public void setPrincipal(Boolean principal) {
        this.enderecoPrincipal = principal;
    }
}