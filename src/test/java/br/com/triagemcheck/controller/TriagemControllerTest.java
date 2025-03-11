package br.com.triagemcheck.controller;

import br.com.triagemcheck.controllers.TriagemController;
import br.com.triagemcheck.dtos.TriagemCompletaRecordDto;
import br.com.triagemcheck.dtos.TriagemRecordDto;
import br.com.triagemcheck.enums.CorProtocolo;
import br.com.triagemcheck.enums.Severidade;
import br.com.triagemcheck.models.TriagemModel;
import br.com.triagemcheck.services.TriagemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TriagemControllerTest {

    @InjectMocks
    TriagemController triagemController;

    @Mock
    TriagemService triagemService;

    @Mock
    Errors errors;

    UUID pacienteId;
    UUID profissionalId;
    UUID triagemId;
    TriagemRecordDto triagemRecordDto;
    Page<TriagemModel> triagemModelPage;
    Page<TriagemCompletaRecordDto> triagemCompletaRecordDtoPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pacienteId = UUID.randomUUID();
        profissionalId = UUID.randomUUID();
        triagemId = UUID.randomUUID();

        // Ajuste aqui para passar os parâmetros corretos ao construtor
        triagemRecordDto = new TriagemRecordDto(
                "Dor de cabeça",
                Severidade.EMERGENCIA,
                CorProtocolo.VERMELHO,
                UUID.fromString("58b4e98d-13af-44e8-9357-f3301f17eef6"));

        triagemModelPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);
        triagemCompletaRecordDtoPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);
    }

    @Test
    void testSalvarTriagem() {
        when(triagemService.save(triagemRecordDto, pacienteId, profissionalId))
                .thenReturn(new TriagemModel());

        ResponseEntity<Object> response = triagemController.savarTriagem(pacienteId, profissionalId, triagemRecordDto, errors);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(triagemService, times(1)).save(triagemRecordDto, pacienteId, profissionalId);
    }

    @Test
    void testGetAll() {
        when(triagemService.findAll(any(PageRequest.class)))
                .thenReturn(triagemModelPage);

        ResponseEntity<Page<TriagemModel>> response = triagemController.getAll(PageRequest.of(0, 10));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(triagemModelPage, response.getBody());
        verify(triagemService, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void testGetOne() {
        UUID triagemId = UUID.randomUUID();
        TriagemModel triagemModel = new TriagemModel();

        when(triagemService.findById(triagemId)).thenReturn(triagemModel);

        ResponseEntity<Object> response = triagemController.getOne(triagemId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(triagemService, times(1)).findById(triagemId);
    }

    @Test
    void testUpdate() {
        when(triagemService.update(triagemRecordDto, triagemId))
                .thenReturn(new TriagemModel());

        ResponseEntity<Object> response = triagemController.update(triagemId, triagemRecordDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(triagemService, times(1)).update(triagemRecordDto, triagemId);
    }

    @Test
    void testGetTriagemCompleta() {
        when(triagemService.findTriagemCompleta(any(PageRequest.class), anyString()))
                .thenReturn(triagemCompletaRecordDtoPage);

        ResponseEntity<Page<TriagemCompletaRecordDto>> response = triagemController.getTriagemCompleta("cpf", PageRequest.of(0, 10));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(triagemCompletaRecordDtoPage, response.getBody());
        verify(triagemService, times(1)).findTriagemCompleta(any(PageRequest.class), anyString());
    }
}
