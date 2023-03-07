package com.example.application.data.service;

import com.example.application.data.entity.PackageModel;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class PackageModelService {

    private final PackageModelRepository repository;

    public PackageModelService(PackageModelRepository repository) {
        this.repository = repository;
    }

    public Optional<PackageModel> get(Long id) {
        return repository.findById(id);
    }

    public PackageModel update(PackageModel entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<PackageModel> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<PackageModel> list(Pageable pageable, Specification<PackageModel> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
