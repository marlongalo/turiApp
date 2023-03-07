package com.example.application.data.service;

import com.example.application.data.entity.PackageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PackageModelRepository
        extends
            JpaRepository<PackageModel, Long>,
            JpaSpecificationExecutor<PackageModel> {

}
