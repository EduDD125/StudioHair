package com.example.sistemacabeleleiro.application.repository.inmemory;

import com.example.sistemacabeleleiro.domain.entities.cpf.CPF;
import com.example.sistemacabeleleiro.domain.entities.client.Client;
import com.example.sistemacabeleleiro.domain.usecases.client.ClientDAO;

import java.util.*;

public class InMemoryClientDAO implements ClientDAO {

    private static final Map<Integer, Client> db = new LinkedHashMap<>();
    private static int idCounter;

    @Override
    public Integer create(Client client) {
        idCounter++;
        client.setId(idCounter);
        db.put(idCounter, client);
        return client.getId();
    }

    @Override
    public boolean update(Client client) {
        int id = client.getId();
        if(db.containsKey(id)){
            db.replace(id, client);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        if(db.containsKey(key)){
            db.remove(key);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Client client) {
        return deleteByKey(client.getId());
    }

    @Override
    public Optional<Client> findOne(Integer key) {
        if(db.containsKey(key))
            return Optional.of(db.get(key));
        return Optional.empty();
    }

    @Override
    public List<Client> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean inactivate(Client client) {
        client.inactivateStatus();
        return update(client);
    }

    @Override
    public boolean activate(Client client) {
        client.activateStatus();
        return update(client);
    }

    @Override
    public Optional<Client> findOneByCPF(CPF cpf) {
        return db.values().stream().
                filter( client -> client.getCpf().equals(cpf)).findAny();
    }

    @Override
    public Optional<Client> findOneByName(String name) {
        return db.values().stream().
                filter(client -> client.getName().equals(name)).findAny();
    }
}
