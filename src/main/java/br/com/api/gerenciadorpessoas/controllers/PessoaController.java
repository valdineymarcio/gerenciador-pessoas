package br.com.api.gerenciadorpessoas.controllers;

import br.com.api.gerenciadorpessoas.dtos.EnderecoDTO;
import br.com.api.gerenciadorpessoas.dtos.PessoaDTO;
import br.com.api.gerenciadorpessoas.models.Endereco;
import br.com.api.gerenciadorpessoas.services.PessoaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    public ResponseEntity<PessoaDTO> criarPessoa(@RequestBody PessoaDTO pessoaDTO) {
        PessoaDTO pessoaCriada = pessoaService.criarPessoa(pessoaDTO);
        PessoaDTO pessoaCriadaDTO = mapToDTO(pessoaCriada);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaCriadaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaDTO> editarPessoa(@PathVariable Long id, @RequestBody PessoaDTO pessoaDTO) {
        PessoaDTO pessoaEditada = pessoaService.editarPessoa(id, pessoaDTO);
        PessoaDTO pessoaEditadaDTO = mapToDTO(pessoaEditada);
        return ResponseEntity.ok(pessoaEditadaDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> consultarPessoa(@PathVariable Long id) {
        PessoaDTO pessoaConsultada = pessoaService.consultarPessoa(id);
        PessoaDTO pessoaConsultadaDTO = mapToDTO(pessoaConsultada);
        return ResponseEntity.ok(pessoaConsultadaDTO);
    }

    @GetMapping
    public ResponseEntity<List<PessoaDTO>> listarPessoas() {
        List<PessoaDTO> pessoas = pessoaService.listarPessoas();
        List<PessoaDTO> pessoasDTO = pessoas.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(pessoasDTO);
    }

    @PostMapping("/{id}/enderecos")
    public ResponseEntity<PessoaDTO> criarEndereco(@PathVariable Long id, @RequestBody EnderecoDTO enderecoDTO) {
        EnderecoDTO enderecoCriado = pessoaService.criarEndereco(id, enderecoDTO);
        PessoaDTO enderecoCriadoDTO = mapToDTO(enderecoCriado);
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoCriadoDTO);
    }

    @GetMapping("/{id}/enderecos")
    public ResponseEntity<List<EnderecoDTO>> listarEnderecos(@PathVariable Long id) {
        List<EnderecoDTO> enderecos = pessoaService.listarEnderecos(id);
        List<EnderecoDTO> enderecosDTO = enderecos.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(enderecosDTO);
    }

    @PutMapping("/{idPessoa}/enderecos/{idEndereco}")
    public ResponseEntity<PessoaDTO> editarEndereco(@PathVariable Long idPessoa, @PathVariable Long idEndereco, @RequestBody EnderecoDTO enderecoDTO) {
        EnderecoDTO enderecoEditado = pessoaService.editarEndereco(idPessoa, idEndereco, enderecoDTO);
        PessoaDTO enderecoEditadoDTO = mapToDTO(enderecoEditado);
        return ResponseEntity.ok(enderecoEditadoDTO);
    }

    @PutMapping("/{idPessoa}/enderecos/{idEndereco}/principal")
    public ResponseEntity<Void> definirEnderecoPrincipal(@PathVariable Long idPessoa, @PathVariable Long idEndereco) {
        pessoaService.marcarEnderecoPrincipal(idPessoa, idEndereco);
        return ResponseEntity.ok().build();
    }


    private PessoaDTO mapToDTO(EnderecoDTO pessoa) {
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(pessoa.getId());
        pessoaDTO.setNome(pessoa.getNome());
        pessoaDTO.setDataNascimento(pessoa.getDataNascimento());
        return pessoaDTO;
    }

    private EnderecoDTO mapToDTO(Endereco endereco) {
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setId(endereco.getId());
        enderecoDTO.setLogradouro(endereco.getLogradouro());
        enderecoDTO.setCep(endereco.getCep());
        enderecoDTO.setNumero(endereco.getNumero());
        enderecoDTO.setCidade(endereco.getCidade());
        return enderecoDTO;
    }
}


