package br.com.api.gerenciadorpessoas.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EnderecoDTO {

    private Long id;

    private String logradouro;

    private String cep;

    private String numero;

    private String cidade;
    private Long pessoaId;
    private Boolean enderecoPrincipal;


    public void setPessoaId(Long pessoaId) {
        this.pessoaId = pessoaId;
    }

    public Long getPessoaId() {
        return pessoaId;
    }


    public void setPrincipal(Boolean principal) {
        this.enderecoPrincipal = principal;
    }

    public Boolean isPrincipal() {
        return enderecoPrincipal;
    }
}


