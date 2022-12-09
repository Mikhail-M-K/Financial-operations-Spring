package com.example.finoper.service;

import com.example.finoper.exceptions.NoSuchObjectException;
import com.example.finoper.model.Client;
import com.example.finoper.model.ClientAccount;
import com.example.finoper.model.dto.ClientAccountCreateDto;
import com.example.finoper.model.dto.ClientAccountDto;
import com.example.finoper.model.dto.ClientCreateDto;
import com.example.finoper.model.dto.ClientDto;
import com.example.finoper.repos.ClientAccountRepo;
import com.example.finoper.repos.ClientRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService{

    private final ClientRepo clientRepo;

    private final ClientAccountRepo clientAccountRepo;

    private PasswordEncoder passwordEncoder;

    public ClientServiceImpl(ClientRepo clientRepo, ClientAccountRepo clientAccountRepo, PasswordEncoder passwordEncoder) {
        this.clientRepo = clientRepo;
        this.clientAccountRepo = clientAccountRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public List<ClientDto> readAll() {
        return clientRepo
                .findAll()
                .stream()
                .map(this::convertToClientDTO)
                .collect(Collectors.toList());
    }
    @Transactional
    @Override
    public ClientDto read(Long id){
        Client client = clientRepo.findById(id)
                .orElseThrow(() -> new NoSuchObjectException("There is no client with ID = " + id + " in Database"));
        return convertToClientDTO(client);
    }

    @Transactional
    @Override
    public List<ClientAccountDto> readAccount(Long id) {
        Client client = clientRepo.findById(id)
                .orElseThrow(() -> new NoSuchObjectException("There is no client with ID = " + id + " in Database"));

       return client.getClientAccount()
               .stream()
               .map(this::convertAccount)
               .collect(Collectors.toList());
    }


    @Transactional
    @Override
    public void create(ClientCreateDto client) {
        clientRepo.save(convertToClientDTO(client));
    }

    public void createClientAccount(ClientAccountCreateDto clientAccountDto) {
        Client client = clientRepo.getReferenceById(clientAccountDto.getClientId());
        ClientAccount clientAccount = clientAccountDtoToClientAccount(clientAccountDto);
        clientAccount.setOpeningDate(LocalDateTime.now());
        clientAccount.setClient(client);
        clientAccountRepo.save(clientAccount);
    }

    private ClientAccount clientAccountDtoToClientAccount(ClientAccountCreateDto clientAccountDto) {
        ClientAccount clientAccount = new ClientAccount();
        clientAccount.setAccountNumber(clientAccountDto.getAccountNumber());
        clientAccount.setSum(clientAccountDto.getSum());
        clientAccount.setTypeAccount(clientAccountDto.getTypeAccount());
        clientAccount.setValidityPeriod(clientAccountDto.getValidityPeriod());
        return clientAccount;
    }

    private Client convertToClientDTO(ClientCreateDto clientCreateDto) {
        Client client = new Client();
        client.setId(clientCreateDto.getId());
        client.setSecondName(clientCreateDto.getSecondName());
        client.setName(clientCreateDto.getName());
        client.setPatronymic(clientCreateDto.getPatronymic());
        client.setSecretWord(passwordEncoder.encode(clientCreateDto.getSecretWord()));
        return client;
    }

    private ClientDto convertToClientDTO(Client client) {
        ClientDto clientDto = new ClientDto();
        clientDto.setId(client.getId());
        clientDto.setSecondName(client.getSecondName());
        clientDto.setName(client.getName());
        clientDto.setPatronymic(client.getPatronymic());
        return clientDto;
    }

    private ClientAccountDto convertAccount(ClientAccount clientAccount) {
        ClientAccountDto clientAccountDto = new ClientAccountDto();
        clientAccountDto.setAccountNumber(clientAccount.getAccountNumber());
        clientAccountDto.setSum(clientAccount.getSum());
        clientAccountDto.setTypeAccount(clientAccount.getTypeAccount());
        clientAccountDto.setOpeningDate(clientAccount.getOpeningDate());
        clientAccountDto.setValidityPeriod(clientAccount.getValidityPeriod());
        clientAccountDto.setClientId(clientAccount.getClient().getId());
        clientAccountDto.setId(clientAccount.getId());
        return clientAccountDto;
    }
}
