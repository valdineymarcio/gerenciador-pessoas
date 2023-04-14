package br.com.api.gerenciadorpessoas.services;

import br.com.api.gerenciadorpessoas.dtos.EnderecoDTO;
import br.com.api.gerenciadorpessoas.dtos.PessoaDTO;
import br.com.api.gerenciadorpessoas.models.Endereco;
import br.com.api.gerenciadorpessoas.models.Pessoa;
import br.com.api.gerenciadorpessoas.repositories.EnderecoRepository;
import br.com.api.gerenciadorpessoas.repositories.PessoaRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final EnderecoRepository enderecoRepository;


    public PessoaService(PessoaRepository pessoaRepository, EnderecoRepository enderecoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.enderecoRepository = enderecoRepository;
    }

    public PessoaDTO criarPessoa(PessoaDTO pessoaDTO) {
        Pessoa pessoa = mapToEntity(pessoaDTO);
        pessoa = pessoaRepository.save(pessoa);
        return mapToDTO(pessoa);
    }

    public PessoaDTO editarPessoa(Long id, PessoaDTO pessoaDTO) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com ID " + id));
        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setDataNascimento(pessoaDTO.getDataNascimento());
        pessoa = pessoaRepository.save(pessoa);
        return mapToDTO(pessoa);
    }

    public PessoaDTO consultarPessoa(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com ID " + id));
        return mapToDTO(pessoa);
    }

    public List<PessoaDTO> listarPessoas() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return pessoas.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public EnderecoDTO criarEndereco(Long pessoaId, EnderecoDTO enderecoDTO) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com ID " + pessoaId));
        Endereco endereco = mapToEntity(enderecoDTO).getEnderecoPrincipal();
        endereco.setPessoa(pessoa);
        endereco = enderecoRepository.save(endereco);
        return mapToDTO(endereco);
    }

    public List<EnderecoDTO> listarEnderecos(Long pessoaId) {
        List<Endereco> enderecos = enderecoRepository.findByPessoaId(pessoaId);
        return enderecos.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public EnderecoDTO marcarEnderecoPrincipal(Long pessoaId, Long enderecoId) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com ID " + pessoaId));
        Endereco endereco = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado com ID " + enderecoId));
        pessoa.setEnderecoPrincipal(endereco);
        pessoa = pessoaRepository.save(pessoa);
        return mapToDTO(pessoa.getEnderecoPrincipal());
    }


    private PessoaDTO mapToDTO(Pessoa pessoa) {
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(pessoa.getId());
        pessoaDTO.setNome(pessoa.getNome());
        pessoaDTO.setDataNascimento(pessoa.getDataNascimento());
        return pessoaDTO;
    }

    private Pessoa mapToEntity(PessoaDTO pessoaDTO) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setDataNascimento(pessoaDTO.getDataNascimento());
        return pessoa;
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
    public PessoaDTO setEnderecoPrincipal(Long pessoaId, Long enderecoId) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com ID " + pessoaId));

        Endereco endereco = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado com ID " + enderecoId));

        if (!pessoa.getEnderecos().contains(endereco)) {
            throw new IllegalArgumentException("Endereço não pertence a essa pessoa");
        }

        pessoa.setEnderecoPrincipal(endereco);
        pessoa = pessoaRepository.save(pessoa);

        return mapToDTO(pessoa);
    }
    public EnderecoDTO editarEndereco(Long enderecoId, Long idEndereco, EnderecoDTO enderecoDTO) {
        Endereco endereco = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado com ID " + enderecoId));

        endereco.setLogradouro(enderecoDTO.getLogradouro());
        endereco.setCep(enderecoDTO.getCep());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setCidade(enderecoDTO.getCidade());

        endereco = enderecoRepository.save(endereco);

        return mapToDTO(endereco);
    }

    public EnderecoDTO consultarEndereco(Long pessoaId, Long enderecoId) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com o ID: " + pessoaId));
        EnderecoDTO enderecoDTO = enderecoService.consultarEndereco(enderecoId);
        if (!enderecoDTO.getPessoaId().equals(pessoaId)) {
            throw new ResourceNotFoundException("Endereço não encontrado com o ID: " + enderecoId);
        }
        return enderecoDTO;
    }
}

