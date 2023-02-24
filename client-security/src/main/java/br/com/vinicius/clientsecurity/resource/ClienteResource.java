package br.com.vinicius.clientsecurity.resource;

import br.com.vinicius.clientsecurity.entities.Cliente;
import br.com.vinicius.clientsecurity.request.ClienteRequest;
import br.com.vinicius.clientsecurity.response.ClienteResponse;
import br.com.vinicius.clientsecurity.services.ClientService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "cliente")
public class ClienteResource {
    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<ClienteResponse> save(@RequestBody ClienteRequest clienteRequest) {
        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(clienteRequest, cliente);
        clientService.save(cliente);

        ClienteResponse clienteResponse = new ClienteResponse();
        BeanUtils.copyProperties(cliente, clienteResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponse);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> findAll() {
        List<Cliente> listaCliente = clientService.findAll();

        List<ClienteResponse> listaClienteResponse = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        listaCliente.forEach(cliente -> listaClienteResponse.add(mapper.map(cliente, ClienteResponse.class)));

        return ResponseEntity.ok(listaClienteResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> findById(@PathVariable(name = "id") UUID id) throws Exception {
        Cliente cliente = null;
        try {
            cliente = clientService.findById(id);

            ClienteResponse clienteResponse = new ClienteResponse();
            BeanUtils.copyProperties(cliente, clienteResponse);

            return ResponseEntity.ok().body(clienteResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ClienteResponse.builder().error(e.getMessage()).build());
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ClienteResponse> findByEmail(@PathVariable(name = "email") String email) throws Exception {
        try {
            Cliente cliente = clientService.findByEmail(email);

            ClienteResponse clienteResponse = new ClienteResponse();
            BeanUtils.copyProperties(cliente, clienteResponse);

            return ResponseEntity.ok().body(clienteResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ClienteResponse.builder().error(e.getMessage()).build());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> update(@PathVariable(name = "id") UUID id, @RequestBody ClienteRequest clienteRequest) throws Exception {

        try {
            Cliente cliente = clientService.findById(id);
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(clienteRequest, cliente);
            cliente.setId(id);
            clientService.save(cliente);

            ClienteResponse clienteResponse = new ClienteResponse();
            BeanUtils.copyProperties(cliente, clienteResponse);

            return ResponseEntity.status(HttpStatus.OK).body(clienteResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ClienteResponse.builder().error(e.getMessage()).build());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") UUID id) {
        try {
            clientService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(ClienteResponse.builder().error("Cliente não existe ou invalido: " + id).build());
        }
    }


}
