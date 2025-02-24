package br.com.TriagemCheck.controller;

import br.com.TriagemCheck.controllers.ResultClinicoContoller;
import br.com.TriagemCheck.dtos.ResultClinicoRecordDto;
import br.com.TriagemCheck.models.ResultClinicosModel;
import br.com.TriagemCheck.services.ResultClinicoService;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ResultClinicoContollerTest {

    @InjectMocks
    ResultClinicoContoller resultClinicoContoller;

    @Mock
    ResultClinicoService resultClinicoService;

    @Mock
    Errors errors;

    UUID triagemId;
    UUID resultadoId;
    ResultClinicoRecordDto resultClinicoRecordDto;
    Page<ResultClinicosModel> resultClinicosModelPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        triagemId = UUID.randomUUID();
        resultadoId = UUID.randomUUID();

        // Ajuste aqui para passar os parâmetros corretos ao construtor
        resultClinicoRecordDto = new ResultClinicoRecordDto(
                "Pneumonia",
                "Antibióticos",
                "Em Tratamento");

        resultClinicosModelPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);
    }

    @Test
    void testSalvarResultClinico() {
        when(resultClinicoService.save(resultClinicoRecordDto, triagemId))
                .thenReturn(new ResultClinicosModel());

        ResponseEntity<Object> response = resultClinicoContoller.salvarResultClinico(triagemId, resultClinicoRecordDto );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(resultClinicoService, times(1)).save(resultClinicoRecordDto, triagemId);
    }

    @Test
    void testGetAll() {
        when(resultClinicoService.findAll(any(PageRequest.class)))
                .thenReturn(resultClinicosModelPage);

        ResponseEntity<Page<ResultClinicosModel>> response = resultClinicoContoller.getAll(PageRequest.of(0, 10));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resultClinicosModelPage, response.getBody());
        verify(resultClinicoService, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void testGetOne() {
        when(resultClinicoService.findById(resultadoId))
                .thenReturn(Optional.of(new ResultClinicosModel()));

        ResponseEntity<Object> response = resultClinicoContoller.getOne(resultadoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(resultClinicoService, times(1)).findById(resultadoId);
    }

    @Test
    void testUpdate() {
        when(resultClinicoService.update(resultClinicoRecordDto, resultadoId))
                .thenReturn(new ResultClinicosModel());

        ResponseEntity<Object> response = resultClinicoContoller.update(resultadoId, resultClinicoRecordDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(resultClinicoService, times(1)).update(resultClinicoRecordDto, resultadoId);
    }
}
