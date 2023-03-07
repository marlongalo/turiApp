package com.example.application.data.service;

import com.example.application.data.entity.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClientModelRepository extends JpaRepository<ClientModel, Long>, JpaSpecificationExecutor<ClientModel> {

}
