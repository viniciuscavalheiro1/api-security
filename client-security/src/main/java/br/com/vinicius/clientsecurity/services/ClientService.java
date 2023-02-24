package br.com.vinicius.clientsecurity.services;

import br.com.vinicius.clientsecurity.entities.Cliente;
import br.com.vinicius.clientsecurity.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {
    private final ClienteRepository clienteRepository;

    public ClientService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public void deleteById(UUID id) {
        clienteRepository.deleteById(id);
    }

    public Cliente findById(UUID id) throws  Exception {
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);
        return optionalCliente.orElseThrow(() -> new Exception("Cliente não encontrado com id: " + id));
    }

    public Cliente findByEmail(String email) throws Exception {
        Optional<Cliente> optionalCliente = clienteRepository.findByEmail(email);
        return optionalCliente.orElseThrow(() -> new Exception("Não existe cliente com esse email: " + email));
    }
}
