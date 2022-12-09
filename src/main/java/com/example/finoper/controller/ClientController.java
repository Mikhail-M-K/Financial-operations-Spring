package com.example.finoper.controller;

import com.example.finoper.model.dto.*;
import com.example.finoper.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping(value="/clients")
    public List<ClientDto> readAll() {
        return clientService.readAll();
    }

    /**
     * Получение данных по клиенту
     * @param id идентификатор клиента
     * @return данные по клиенту
     */
    @GetMapping(value="/client/{id}")
    public ClientDto read(@PathVariable(name="id") Long id) {
        return clientService.read(id);
    }

    /**
     * Получение данных по счету клиента
     * @param id идентификатор клиента
     * @return данные по счетам клиента
     */
    @GetMapping(value="/client/accounts/{id}")
    public List<ClientAccountDto> readAccount(@PathVariable(name="id") Long id) {
        return clientService.readAccount(id);
    }

    @PostMapping(value="/client")
    public void create(@RequestBody ClientDto clientDto) {
        clientService.create(clientDto);
    }

    @PostMapping(value="/clientaccount")
    public void createClientAccount(@RequestBody ClientAccountRequestDto clientAccountRequestDto) {
        clientService.createClientAccount(clientAccountRequestDto);
    }

}
