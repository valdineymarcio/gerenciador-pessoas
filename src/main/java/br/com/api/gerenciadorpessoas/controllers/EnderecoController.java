package br.com.api.gerenciadorpessoas.controllers;

import br.com.api.gerenciadorpessoas.dtos.EnderecoDTO;
import br.com.api.gerenciadorpessoas.models.Endereco;
import br.com.api.gerenciadorpessoas.services.PessoaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    private final PessoaService pessoaService;

    public EnderecoController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> consultarEndereco(@PathVariable Long id) {
        Endereco endereco = pessoaService.consultarEndereco(id);
        EnderecoDTO enderecoDTO = mapToDTO(endereco);
        return ResponseEntity.ok(enderecoDTO);
    }

    private EnderecoDTO mapToDTO(Endereco endereco) {
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setId(endereco.getId());
        enderecoDTO.setLogradouro(endereco.getLogradouro());
        enderecoDTO.setCep(endereco.getCep());
        enderecoDTO.setNumero(endereco.getNumero());
        enderecoDTO.setCidade(endereco.getCidade());
        enderecoDTO.setPessoaId(endereco.getPessoa().getId());
        enderecoDTO.setPrincipal(endereco.isPrincipal());
        return enderecoDTO;
    }

}