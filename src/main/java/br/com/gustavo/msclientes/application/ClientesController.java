package br.com.gustavo.msclientes.application;

import br.com.gustavo.msclientes.application.representation.ClienteSaveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
@Slf4j
public class ClientesController {

    private final ClienteService service;

    @GetMapping
    public String status() {
        log.info("Obtendo o status do microservice de clientes");
        return "ok";
    }

    // metodo para salvar dados do cliente, com ClienteSaveRequest (cpf, nome e idade)
    @PostMapping
    public ResponseEntity save(@RequestBody ClienteSaveRequest request) {
        var cliente = request.toModel();
        service.save(cliente);
        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("cpf={cpf}")
                .buildAndExpand(cliente.getCpf())
                .toUri();
        return ResponseEntity.created(headerLocation).build();
    }

    @GetMapping(params = "cpf")
    public ResponseEntity dadosCliente(@RequestParam("cpf") String cpf) {
        var cliente = service.getByCPF(cpf);

        // lógica para verificar se cpf existe
        // pois pode acontecer de cliente passar cpf que n esteja cadastrado no sistema
        if (cliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }
}
