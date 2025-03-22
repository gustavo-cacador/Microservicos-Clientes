package br.com.gustavo.msclientes.application;

import br.com.gustavo.msclientes.domain.Cliente;
import br.com.gustavo.msclientes.infra.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    @Transactional
    public Cliente save(Cliente cliente) {
        return repository.save(cliente);
    }

    // aqui vamos usar optional, pois ele pode ou não retornar um cliente
    // exemplo: podemos passar um cpf no qual não exista, logo, ele n vai retornar nenhum cliente
    public Optional<Cliente> getByCPF(String cpf) {
        return repository.findByCpf(cpf);
    }
}
