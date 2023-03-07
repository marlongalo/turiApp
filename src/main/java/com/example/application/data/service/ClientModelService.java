package com.example.application.data.service;

import com.example.application.data.entity.ClientModel;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ClientModelService {

    private final ClientModelRepository repository;

    public ClientModelService(ClientModelRepository repository) {
        this.repository = repository;
    }

    public Optional<ClientModel> get(Long id) {
        return repository.findById(id);
    }

    public ClientModel update(ClientModel entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<ClientModel> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<ClientModel> list(Pageable pageable, Specification<ClientModel> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
