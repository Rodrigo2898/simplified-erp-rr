package com.ms.rr.pessoa_service.application.api;

import com.ms.rr.pessoa_service.domain.service.impl.ClienteServiceImpl;
import jakarta.inject.Inject;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClienteApiTest {

    @Inject
    ClienteServiceImpl clienteService;

    @InjectMocks
    ClienteApi clienteApi;


}